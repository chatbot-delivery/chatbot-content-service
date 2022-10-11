# Getting Started

	Chatbot-Content-Service

### Reference Documentation

### Deployment


az appservice plan create --name asp-chatbot-content-service --resource-group RG-FedExEurope_DeliveryBot --location "West Europe" --sku F1

az webapp create --name chatbot-content-service --resource-group RG-FedExEurope_DeliveryBot  --plan asp-chatbot-content-service --runtime "java|1.8|Tomcat|8.5"
  
  
az deployment group create --name chatbot-content-service-deployment --resource-group RG-FedExEurope_DeliveryBot --template-file src/main/resources/templates/infra-template-chatbot-content-service.json --parameters '@src/main/resources/templates/parameters-infra-template-chatbot-content-service.json'

