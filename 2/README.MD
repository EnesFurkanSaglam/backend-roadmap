## Hibernate N+1 Problem and Solution with JOIN FETCH  
Hibernate generated unnecessary queries (N+1 Problem) when fetching related entities. Using **JOIN FETCH**, related data was retrieved in a single query, improving performance.  

## Cache Management with Redis  
To avoid unnecessary database queries, Redis was used to cache frequently accessed data, enhancing application performance.  

## Hibernate N+1 Problemi ve JOIN FETCH ile Çözümü  
Hibernate, ilişkili verileri çekerken gereksiz fazla sorgular (N+1 Problemi) oluşturdu. **JOIN FETCH** kullanılarak ilişkili veriler tek sorguda çekildi ve performans iyileştirildi.  

## Redis ile Cache Yönetimi  
Veritabanına gereksiz sorgu göndermemek için Redis kullanıldı ve sık kullanılan veriler cache’e alınarak uygulama performansı artırıldı.  
