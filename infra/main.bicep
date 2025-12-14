param environmentName string
param location string = resourceGroup().location

var resourceToken = uniqueString(subscription().subscriptionId, resourceGroup().id, location, environmentName)

resource logAnalytics 'Microsoft.OperationalInsights/workspaces@2022-10-01' = {
  name: 'azlaw${resourceToken}'
  location: location
  properties: {
    sku: {
      name: 'PerGB2018'
    }
  }
}

resource applicationInsights 'Microsoft.Insights/components@2020-02-02' = {
  name: 'azai${resourceToken}'
  location: location
  kind: 'web'
  properties: {
    Application_Type: 'web'
    WorkspaceResourceId: logAnalytics.id
  }
}

resource keyVault 'Microsoft.KeyVault/vaults@2023-02-01' = {
  name: 'azkv${resourceToken}'
  location: location
  properties: {
    sku: {
      family: 'A'
      name: 'standard'
    }
    tenantId: subscription().tenantId
    accessPolicies: []
    publicNetworkAccess: 'Enabled'
    enabledForTemplateDeployment: true
  }
}

resource containerRegistry 'Microsoft.ContainerRegistry/registries@2023-07-01' = {
  name: 'azacr${resourceToken}'
  location: location
  sku: {
    name: 'Basic'
  }
  properties: {
    adminUserEnabled: false
  }
}

resource userAssignedManagedIdentity 'Microsoft.ManagedIdentity/userAssignedIdentities@2023-01-31' = {
  name: 'azumi${resourceToken}'
  location: location
}

resource roleAssignmentAcrPull 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(containerRegistry.id, userAssignedManagedIdentity.id, '7f951dda-4ed3-4680-a7ca-43fe172d538d')
  scope: containerRegistry
  properties: {
    roleDefinitionId: subscriptionResourceId('Microsoft.Authorization/roleDefinitions', '7f951dda-4ed3-4680-a7ca-43fe172d538d')
    principalId: userAssignedManagedIdentity.properties.principalId
    principalType: 'ServicePrincipal'
  }
}

resource roleAssignmentKeyVault 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(keyVault.id, userAssignedManagedIdentity.id, 'b86a8fe4-44ce-4948-aee5-eccb2c155cd7')
  scope: keyVault
  properties: {
    roleDefinitionId: subscriptionResourceId('Microsoft.Authorization/roleDefinitions', 'b86a8fe4-44ce-4948-aee5-eccb2c155cd7')
    principalId: userAssignedManagedIdentity.properties.principalId
    principalType: 'ServicePrincipal'
  }
  dependsOn: [
    keyVault
  ]
}

resource mysqlServer 'Microsoft.DBforMySQL/flexibleServers@2022-09-30-preview' = {
  name: 'azmysql${resourceToken}'
  location: location
  sku: {
    name: 'Standard_B1ms'
    tier: 'Burstable'
  }
  properties: {
    administratorLogin: 'adminuser'
    administratorLoginPassword: 'P@ssw0rd123!'
    version: '8.0.21'
    storage: {
      storageSizeGB: 32
    }
    backup: {
      backupRetentionDays: 7
      geoRedundantBackup: 'Disabled'
    }
    highAvailability: {
      mode: 'Disabled'
    }
    network: {
      publicNetworkAccess: 'Enabled'
    }
  }
}

resource mysqlFirewallRule 'Microsoft.DBforMySQL/flexibleServers/firewallRules@2022-09-30-preview' = {
  parent: mysqlServer
  name: 'AllowAllAzureServices'
  properties: {
    startIpAddress: '0.0.0.0'
    endIpAddress: '255.255.255.255'
  }
}

resource containerAppEnvironment 'Microsoft.App/managedEnvironments@2022-03-01' = {
  name: 'azcae${resourceToken}'
  location: location
  properties: {
    daprAIInstrumentationKey: applicationInsights.properties.InstrumentationKey
    appLogsConfiguration: {
      destination: 'log-analytics'
      logAnalyticsConfiguration: {
        customerId: logAnalytics.properties.customerId
        sharedKey: logAnalytics.listKeys().primarySharedKey
      }
    }
  }
}

resource containerApp 'Microsoft.App/containerApps@2022-03-01' = {
  name: 'azca${resourceToken}'
  location: location
  identity: {
    type: 'UserAssigned'
    userAssignedIdentities: {
      '${userAssignedManagedIdentity.id}': {}
    }
  }
  properties: {
    managedEnvironmentId: containerAppEnvironment.id
    configuration: {
      secrets: [
        {
          name: 'mysql-connection-string'
          value: 'jdbc:mysql://${mysqlServer.properties.fullyQualifiedDomainName}:3306/student_management_system?user=adminuser&password=P@ssw0rd123!'
        }
      ]
      registries: [
        {
          server: containerRegistry.properties.loginServer
          identity: userAssignedManagedIdentity.id
        }
      ]
      ingress: {
        external: true
        targetPort: 8080
        corsPolicy: {
          allowedOrigins: ['*']
          allowedMethods: ['GET', 'POST', 'PUT', 'DELETE']
          allowedHeaders: ['*']
        }
      }
    }
    template: {
      containers: [
        {
          name: 'studentmanagementsystem'
          image: 'mcr.microsoft.com/azuredocs/containerapps-helloworld:latest'
          resources: {
            cpu: '0.5'
            memory: '1.0Gi'
          }
          env: [
            {
              name: 'SPRING_DATASOURCE_URL'
              secretRef: 'mysql-connection-string'
            }
          ]
        }
      ]
      scale: {
        minReplicas: 1
        maxReplicas: 10
      }
    }
  }
  dependsOn: [
    roleAssignmentAcrPull
    mysqlServer
  ]
}

output RESOURCE_GROUP_ID string = resourceGroup().id
output AZURE_CONTAINER_REGISTRY_ENDPOINT string = containerRegistry.properties.loginServer
