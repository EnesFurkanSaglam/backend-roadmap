# Sharding Example

This project demonstrates the concept of **sharding** (splitting data across different databases) in a **Spring Boot** application. It routes incoming user requests to the appropriate database based on the **user ID**.

## Purpose
- Show how to horizontally scale databases using a sharding technique.
- Redirect requests to different databases using **dynamic data source routing**, based on user ID.
- Demonstrate how to manage multiple data sources (shard1 and shard2) with **Spring Boot** and **Spring Data JPA**.

## How It Works

### ContextHolder & ShardRoutingService
- `ShardRoutingService` decides which shard (shard1 or shard2) will be used based on the incoming `userId`.
- The `ContextHolder` class stores the active shard information in a `ThreadLocal` variable.

### DynamicDataSource
- Extends Spring’s `AbstractRoutingDataSource`.
- In `determineCurrentLookupKey()`, it returns the shard information from `ContextHolder`, allowing Spring Data to choose the correct data source.

### DatabaseConfig
- Contains `DataSource` objects for `shard1` and `shard2`.
- In `dynamicDataSource`, these data sources are put into a `targetDataSources` map so that Spring can pick the correct one at runtime.

### UserController & UserService
- Provides a simple **REST** interface.
- When creating a user with a `POST` request or fetching a user with a `GET` request, the `ShardRoutingService` is called first to select the correct shard.
- Data operations (create/read) are then performed on the appropriate database.

### User Entity
- Represents a sample user table.
- You can specify the table name with `@Table(name = "users")` or a similar approach to ensure smooth table creation in the database.

---

# Sharding Uygulaması Örneği

Bu proje, **Spring Boot** tabanlı bir uygulamada **sharding** (veriyi parçalara bölerek farklı veritabanlarında tutma) konseptini örneklemek amacıyla hazırlanmıştır. Uygulama, gelen kullanıcı isteklerini kullanıcı **ID’sine** göre ilgili veritabanına yönlendirecek basit bir mekanizmaya sahiptir.

## Amaç
- Veritabanlarını yatay ölçekleyebilmek için sharding tekniğini göstermek.
- Kullanıcı ID’sine göre isteklerin **dynamic data source routing** kullanılarak farklı veritabanına aktarılmasını sağlamak.
- **Spring Boot** ve **Spring Data JPA** kullanarak çoklu veri kaynağı (shard1 ve shard2) yönetimini uygulamalı olarak göstermek.

## Nasıl Çalışır?

### ContextHolder & ShardRoutingService
- `ShardRoutingService`, gelen `userId` değeri üzerinden, hangi shard’ın (shard1 veya shard2) kullanılacağına karar verir.
- `ContextHolder` sınıfındaki `ThreadLocal` değişken sayesinde aktif shard bilgisini saklar.

### DynamicDataSource
- Spring’in `AbstractRoutingDataSource` sınıfından türetilmiştir.
- `determineCurrentLookupKey()` metodunda, `ContextHolder` içerisindeki shard bilgisi döndürülür. Böylece Spring Data, hangi veri kaynağının kullanılacağını anlar.

### DatabaseConfig
- `shard1` ve `shard2` için tanımlanan `DataSource` nesnelerini içerir.
- `dynamicDataSource` metodu ile `targetDataSources` haritasına ekleyip, Spring’in runtime’da seçim yapmasını sağlar.

### UserController & UserService
- Basit bir **REST** arayüzü sağlar.
- `POST` isteği ile kullanıcı eklenirken veya `GET` isteği ile kullanıcı sorgulanırken, önce `ShardRoutingService` çağrılır ve doğru shard seçilir.
- Sonrasında veri kaydetme/sorgulama işlemi uygun veritabanında gerçekleşir.

### User Entity
- Örnek bir kullanıcı tablosunu temsil eder.
- Uygulamada tablo adının veritabanında sorunsuz oluşabilmesi için `@Table(name = "users")` veya benzeri bir yaklaşım kullanılabilir.




