## Merhabalar

Öncelikle sistemin çalışma şeklinden bahsetmek istiyorum.<br/>  
**Not: Her müşterinin bir sepeti vardır şeklinde tasarladım. Bu yüzden artık müşteri kimliği aynı zamanda sepeti de temsil ediyor.** <br/>  
1- Sepete ekleme yapılacağı zaman arka tarafta herhangi bir sepet yoksa,
  müşteriye sepet oluşturuluyor ve eklenmek istenen ürün sepete ekleniyor.<br/>**POST basket/add**&nbsp;&nbsp;(Sepetin ara toplamı hesaplanıyor ve kampanya servisine sorgu atılıyor)<br/>  
2- Sepete ekleme yapılırken müşterinin hali hazırda açık bir sepeti varsa, sepetteki ürünler kontrol ediliyor. Sepette olan bir ürün ise miktarı arttırılıyor. Değilse müşterinin sepetine yeni ürün ekleniyor.<br/>**POST basket/add**&nbsp;&nbsp;(Sepetin ara toplamı hesaplanıyor ve kampanya servisine sorgu atılıyor)<br/>  
3- Sepet güncelleme işlevi sadece sepette yapılabiliyor ve ürünün adedini arttırma ve azaltma şeklinde kullanılabiliyor<br/>**POST basket/update**&nbsp;&nbsp;(Sepetin ara toplamı hesaplanıyor ve kampanya servisine sorgu atılıyor<br/>***Önemli: Güncellenen ürünün adedi sepetteki toplam adet şeklinde gönderilmeli***<br/>***Önemli2: Sepette 1 adet kalmış ürünler için silme işlevi kullanılmalı***<br/>  
4- Sepetteki ürünü silebilmek için &nbsp;&nbsp;**POST basket/delete**&nbsp;&nbsp;(Sepetin ara toplamı hesaplanıyor ve kampanya servisine sorgu atılıyor<br/>  
5- Sepetteki ürünleri çekebilmek için  &nbsp;&nbsp;**POST basket/{customerId}**<br/>  
  
## Kurulum

Ürün ve Kampanya servislerinde db olarak postgres kullanılmıştır (Yapılmak istenen sepet servisi olduğu için çok özen gösterilmedi. Aynı dbyi kullanıyorlar). Postgres'i ayağa kaldırmak için

```bash
docker run --name postgrescik -e POSTGRES_PASSWORD=pass123456 -p 5432:5432 -d postgres
```
Ürün servislerinde, fiyat ve stok değişimlerini yayınlamak için Kafka kullanılmıştır.
```bash
docker run --name kafkacik -p 2181:2181 -p 9092:9092 -e ADVERTISED_HOST=127.0.0.1  -e NUM_PARTITIONS=10 johnnypark/kafka-zookeeper
```
Sepet servisinde veritabanı olarak Redis kullanılmıştır. Redisi ayağa kaldırmak için
```bash
docker container run --name rediscik -p 6379:6379 -d redis
```

**NOT: Docker compose yapmak isterdim ama planlamayı düzgün yapamadığım için pek araştırmaya vaktim kalmadı.**

## Kullanım

```
Postman istekleri repoya dahil edildi
```

## License
[MIT](https://choosealicense.com/licenses/mit/)
