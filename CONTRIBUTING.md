# Halka Arzlar

- Uygulama geliştirmeye hızlı bir giriş için ön bakış. Lütfen sorunları çözmekle vakit harcamıyacaksanız  [geliştirici kuralları](https://github.com/bdrtr/Halka_arzlar/blob/main/CONTRIBUTING.md#geli%C5%9Ftirici-kurallar%C4%B1) bölümüne göz atın.

- 1-) Java 
- 2-) MVVM 
- 3-) DataBinding 
- 4-) Volley
- 5-) Jsoup
- 6-) SQLite


# Başlıca çözülmesi gereken problemer

__SORUN 1__ Sqlite'a gelen verilerden aynı id'ye sahip olanlar tekrar veri tabanına kayıt edilmemelidir.

## Ekran Görüntüleri

![Uygulama Ekran Görüntüsü](https://user-images.githubusercontent.com/69633060/241162077-34ccdbc1-d829-4ede-9203-953bce6e193f.jpg)

  
- Burda verilerin bir birini tekrar ettiğini görmek için en az 1 sayfa boyu aşşağı kaydırılması gerekir.

## Bazı çözüm yöntemleri
- Verileri veri tabanına kaydetmeden önce veri tabanını boşaltmaktır.
## 

```java

 public void loadDatas() {
        ArrayList<Stock> stc = myRunnable.returnStockList();
        dbo.AddList(vt,stc,(ArrayList<Stock>) mainViewmodel.returnStocks());
        mainViewmodel.update(dbo.getAllStocks(vt));
    }

```
- burda verileri her __LoadDatas()__ motodundan önce veri tabanından silmek:

```java
dbo.DelALL(vt);
```
- Uygulama ilk açılırken uzun bir süre bekleyeme neden olabilir. Bu da veri tabanı kullanmanın temel amacı olan kullanıcıyı bekletmemek fikriyle zıt düşer.

__SORUN 2__ En büyük sorun iki farklı asenkron çalışan metodun bir biriyle uyumlu çalışması için aralarına yeterli bir zaman farkı konmasıdır.

![Uygulama Ekran Görüntüsü](https://user-images.githubusercontent.com/69633060/241165531-9a9bede3-5c7a-46f8-bed9-7114839ddeff.png)

- bu iki farklı metod her biri kendi içinde asenkron olarak çalışır fakat ikinci metod çalışmak için ilk metotdan gelen _Links_ listesinin içerisinin dolmasına dikkat eder.


![Uygulama Ekran Görüntüsü](https://user-images.githubusercontent.com/69633060/241166247-3057f590-08e8-4c64-ba85-7a67f7fb7ae9.png)

- Burda iki fonksiyonu alt alta yazmak mümkün gözükmez çünkü gerekli olan bekleme süresini sağlayamabilir.

```java
public void run() {
    getURL();
    getStocks();
}
```

- Ama fonksiyon periyodik olarak çağrıldığında __getStocks()__'un olduğu kadar __getURL()__ de çalışması kritiktir.

fonksiyon çağrısı _MainActivitiy_'de şu şekildedir.

```java
public void ConnectnUpdate() {
        tmr = new Timer();
        long delay = 1000*10;
        long period = 1000*10;
        myRunnable.getURL();
        tmr.scheduleAtFixedRate(myRunnable,delay,period);
    }
```

- __getURL()__ metodu bir kere çalıştırılır ama periyodik çalışması için desteklenmez.

__SORUN 3__ Search işlevinin görevini yerine getirmemesidir.

```java
else if (item.getItemId() == R.id.app_search) {
            SearchView sc = findViewById(R.id.app_search);
            sc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    List<Stock> st = new ArrayList<>();
                    for (Stock s: myRunnable.returnStockList()) {
                        if (s.getId().contains(query.toUpperCase())) {{
                            st.add(s);
                        }}
                    }
                    adapter.filter(st);

                    return true;
                }
```
- _app_search_ bir menü itemidir ve kendisine ait bir _Listener_ nesnesi içerir. Bu girilen kelimeyi almak için gereklidir. _adapter_ nesnesi _Recycler View_ ' de tutulan _layout_ için bir sınıfdır.

```java
 public void filter(List<Stock> st) {
        filter.clear();
        this.stocks = st;
        notifyDataSetChanged();
    }
```

- Aldığı listedeki verileri orjinal listenin yerine koyar ve yeni bir liste olarak döndürmesi için onu günceller. 


### Geliştirici Kuralları
- Bu bölümde bu projeye destek vercek her kullanıcın projeyi ortak bir düzen inşaa ederek daha iyi geliştirmesi için gerekli bir takım kurallar tartışılır.

- İlk olarak tüm geliştiricilerden paket adlarına küçük ve sınıf adlarına büyük başlaması beklenir.

![Uygulama Ekran Görüntüsü](https://user-images.githubusercontent.com/69633060/241172352-b595ddca-bff2-4ea1-b6bb-76cfd95eb552.png)

- İkinci olarak çok uzun olmayan ama açıklayıcı değişken isimleri ve bu değişken isimlerinin _ilk kelime küçük diğerleri büyük harfle başlar_ prensibine uygun yazılması beklenir.' _ ' ile ayırcakların kod kısmında en fazla iki kelime için bunu yapması ve devam eden iki kelimenin de _ortadan ayrılıp küçük harfle başlaması_ beklenir.(Sınıf adlarının büyük başlaması ile bu ifadeler birleştirilebilir.)

```java
//ornek 1
setOnDataList = new ...

//ornek 2 
problem_set = ...

//ornek3

UserUseObject binding = new UserUseObject...
```

- Proje teknolojilerine uygun olarak _liveData_ , _DataBinding_ , _ViewModel_ nesnelerine uygun kullanım beklenir.

```java
mainViewmodel.update(dbo.getAllStocks(vt));

/////

private StockRepo stockRepo;

```

- Son olarak yorum satırları kullanmaktan çekinmeyin.
