# chatbot-content-service

** Infra Deployment
set RESOURCE_GROUP_NAME=RG-FedExEurope_DeliveryBot
set APP_SERVICE_PLAN_NAME=asp-chatbot-content-service
set APP_SERVICE_NAME=chatbot-content-service
set LOCATION="West Europe"
set LOG_ANALYTICS_WORKSPACE="chatbot-content-service-workspace"
set APP_INSIGHTS_NAME="chatbot-content-service-app"

az appservice plan create --name %APP_SERVICE_PLAN_NAME% --resource-group %RESOURCE_GROUP_NAME% --location %LOCATION% --sku F1 

az webapp create --name %APP_SERVICE_NAME% --resource-group %RESOURCE_GROUP_NAME% --plan %APP_SERVICE_PLAN_NAME% --runtime "java:1.8:Java SE:8"

az webapp log config --application-logging filesystem --detailed-error-messages true --failed-request-tracing true --resource-group %RESOURCE_GROUP_NAME%  --name %APP_SERVICE_NAME% --level verbose --web-server-logging filesystem

az monitor log-analytics workspace create --resource-group %RESOURCE_GROUP_NAME% -n %LOG_ANALYTICS_WORKSPACE%

az monitor app-insights component create --app %APP_INSIGHTS_NAME% --location %LOCATION% --kind web --resource-group %RESOURCE_GROUP_NAME% --application-type web --workspace %LOG_ANALYTICS_WORKSPACE%

az webapp config appsettings set --name %APP_SERVICE_NAME% --resource-group %RESOURCE_GROUP_NAME% --settings APPINSIGHTS_INSTRUMENTATIONKEY=f0f9b402-5db1-421a-9896-051a9b33514a APPLICATIONINSIGHTS_CONNECTION_STRING=InstrumentationKey=f0f9b402-5db1-421a-9896-051a9b33514a ApplicationInsightsAgent_EXTENSION_VERSION=~2


** Code Deployment   
 Deployment Via CICD (GitHub Actions)
 
 ** Application endpoints
 
 https://chatbot-content-service.azurewebsites.net/dialogs
 https://chatbot-content-service.azurewebsites.net/dialogs?language=en
  https://chatbot-content-service.azurewebsites.net/dialogs?language=en&dialogId=change_delivery_date
 https://chatbot-content-service.azurewebsites.net/dialogs/deliveryoptions
 