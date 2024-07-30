# DefaultApi

All URIs are relative to *https://libraryApp*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createStudent**](DefaultApi.md#createStudent) | **POST** /create-student | POST create-student |


<a name="createStudent"></a>
# **createStudent**
> ResponseApi createStudent(student)

POST create-student

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://libraryApp");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    Student student = new Student(); // Student | 
    try {
      ResponseApi result = apiInstance.createStudent(student);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#createStudent");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **student** | [**Student**](Student.md)|  | |

### Return type

[**ResponseApi**](ResponseApi.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

