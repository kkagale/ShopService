1. * Build Project *
	Build project with eclipse classpath setup

	./gradlew build eclipse
	
2. * Build and run project *

	./gradlew build && java -jar build/libs/shop-service-0.1.0.jar
			OR
	./gradlew bootRun
	
3. * Application Port *
	Application will start on port 9278 which is configurable in application.properties
   with property name server.port=9278
   e.g. http://localhost:9278/v1/shopservice?customerLongitude=83.8536681&customerLatitude=18.4028691
   
4. * Test Using Postman *
	You can test the api through postman client. Refer below steps
		i - Open postman in Chrome Browser
		ii - Import 'api_client_postman.json' file from ShopService/api directory.
		iii - Start using api
		
5. * Swagger API documentation *
	You can access api documentation with below url:
	http://localhost:9278/swagger-ui.html

6. Checklist to consider the API for production ready:
	i) Secure access to the API.
	 	- In order to have secure access to API, the authetication/authorization framework like Oauth 2.0 can be used.
	 	- Use SSL/TLS.
	ii) Choose the data storage system considering requirements like performance, cost, amount of data.
		E.g. Sql or Non-Sql DB.
	iii) If requirement is Highly Available then consider to have multiple instances of API on different server/data centers.
	iv) The Tools like Hystix can be used to wrap external calls inside HystrixCommand to have better control over external dependencies
		like isolation, fallback mechanism etc.
	v) We can register API endpoints to service registry/discovery, so that client can get the API endpoint details.
	vi) Have the acceptance/integration tests ready to verify before making it available to production.
	 
	