# Cashcards OAuth2


## How to run the app

Provide a way to obtain the token
> docker run --rm --name sso -p 9000:9000 ghcr.io/vmware-tanzu-learning/course-secure-rest-api-oauth2-code/sso:latest

Get the token
> http -a cashcard-client:secret --form :9000/oauth2/token grant_type=client_credentials scope=cashcard:read

Use the token to call the APIs i.e.
> curl --location 'localhost:8068/cashcards' \
--header 'Authorization: Bearer eyJ...8Og'