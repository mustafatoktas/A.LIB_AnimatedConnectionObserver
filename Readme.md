# Animated Connection Observer

Jetpack Compose için internet bağlantısı gözlemcisi. Cihazın bağlantısını `Flow` ile izler; bağlantı kopunca ekranın üstünde renkli bir mesaj çubuğu, altında yaylanarak gelen bir bilgi kartı gösterir, içeriği bulanıklaştırır ve dokunmaya kapatır.

## Kurulum

Android 13 (API 33) ve üzeri, Jetpack Compose ve Material 3 gerekir.

**Doğrudan ekleme**

```kotlin
// app/build.gradle.kts
dependencies {
    implementation("com.toktasoft:animated-connection-observer:1.0.2")
}
```

**Sürüm kataloğu (TOML) ile**

```toml
# gradle/libs.versions.toml
[versions]
animatedConnectionObserver = "1.0.2"

[libraries]
animated-connection-observer = { group = "com.toktasoft", name = "animated-connection-observer", version.ref = "animatedConnectionObserver" }
```

```kotlin
// app/build.gradle.kts
dependencies {
    implementation(libs.animated.connection.observer)
}
```

Compose ve Material 3 bağımlılıkları otomatik gelir. Kütüphane ek bir ikon paketi getirmez; kullandığı üç ikon kendi içinde vektör olarak çizilidir. `ACCESS_NETWORK_STATE` izni kütüphanenin manifest'inden uygulamanıza kendiliğinden eklenir.

Proje JDK 25, Gradle 9.8, AGP 9.5 ve Kotlin 2.4.21 ile derlenir. Gradle JDK'sı `gradle/gradle-daemon-jvm.properties` ile 25'e sabitlenmiştir.

## Örnek uygulama

Kütüphaneyi kod yazmadan denemek için örnek uygulamanın APK'sını [Releases](https://github.com/mustafatoktas/A.LIB_AnimatedConnectionObserver/releases/latest) sayfasından indirip kurabilirsiniz. Uygulamada gerçek ağ yerine simülasyon seçerek bağlantıyı tek dokunuşla kesip açabilir; bulanıklık, etkileşim engeli ve kart animasyonu ayarlarını canlı önizlemede deneyebilirsiniz.

## Kullanım

```kotlin
import com.toktasoft.animatedconnectionobserver.composables.AnimatedConnectivityBox
import com.toktasoft.animatedconnectionobserver.composables.observeConnectivity

val isConnected by observeConnectivity()

AnimatedConnectivityBox(
    modifier = Modifier.fillMaxSize(),
    isConnected = isConnected,
    messageBarMessageOffline = stringResource(R.string.no_connection),
    messageBarMessageBackOnline = stringResource(R.string.back_online),
    cardTitleOffline = stringResource(R.string.connection_lost),
    cardMessageOffline = stringResource(R.string.check_network),
    navigationButton = {
        TextButton(onClick = { context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS)) }) {
            Text(stringResource(R.string.open_network_settings))
        }
    },
) {
    AppContent()
}
```

`AnimatedConnectivityBox` ekranın tamamını saracak şekilde tasarlanmıştır: mesaj çubuğu durum çubuğunun, kart da gezinme çubuğunun payını kendisi bırakır.

### Bağlantıyı izleme

`observeConnectivity()` bağlantı durumunu bir Compose `State<Boolean>` olarak verir.

- **Çalışma şekli:** Varsayılan ağ için bir `NetworkCallback` kaydeder ve yaşam döngüsüne bağlı toplar; ekran arka plana geçince dinlemeyi bırakır.
- **Başlangıç değeri:** İlk çizimde o anki durum okunur, böylece açılışta yanlış bir "çevrimdışı" görünmez.
- **"Bağlı" ne demek:** Ağın Android tarafından internete eriştiği doğrulanmış olmalıdır (`NET_CAPABILITY_VALIDATED`). İnternetsiz bir Wi-Fi'ye bağlıyken sonuç `false` olur.

`isConnected` parametresi herhangi bir `Boolean` alır; kendi bağlantı kontrolünüzü veya testte sabit bir değeri de verebilirsiniz.

### Akış

- **Bağlantı koptuğunda:** İçerik hemen bulanıklaşır ve dokunmaya kapanır. `messageBarDelay` sonra kırmızı mesaj çubuğu iner, ardından `cardDelay` sonra bilgi kartı aşağıdan yaylanarak gelir.
- **Bağlantı geldiğinde:** Bulanıklık ve engel hemen kalkar, çubuk yeşile döner. `messageBarDelay` sonra kart iner, `cardDelay` sonra çubuk kapanır.

### Parametreler

| Parametre | Tür | Varsayılan | Açıklama |
|---|---|---|---|
| `isConnected` | `Boolean` | Zorunlu | Bağlantı durumu. Genellikle `observeConnectivity()` sonucudur. |
| `modifier` | `Modifier` | `Modifier` | Kapsayıcıya uygulanır. |
| `blurWhenOffline` | `Boolean` | `true` | Çevrimdışıyken içeriği bulanıklaştırır. |
| `blurRadius` | `Dp` | `14.dp` | Bulanıklık yarıçapı. |
| `overlayWhenOffline` | `Boolean` | `true` | Çevrimdışıyken içeriğin üstüne dokunmaları yutan yarı saydam bir katman koyar. |
| `overlayAlphaWhenOffline` | `Float` | `0.1f` | Katmanın siyah opaklığı (0–1). |
| `messageBarMessageOffline` | `String` | `"No Connection!"` | Bağlantı yokken çubuktaki metin. |
| `messageBarMessageBackOnline` | `String` | `"Back Online"` | Bağlantı gelince çubuktaki metin. |
| `messageBarDelay` | `Long` | `1000` | Durum değiştikten sonra ilk adıma kadar beklenen süre (ms). |
| `messageBarSuccessColor` | `Color` | `#4CAF50` | Çevrimiçi çubuk rengi. |
| `messageBarErrorColor` | `Color` | `#EA1B37` | Çevrimdışı çubuk rengi. Renk geçişi animasyonludur. |
| `barTextColor` | `Color` | Beyaz | Çubuktaki metin ve ikon rengi. |
| `cardTitleOffline` | `String` | `"No Internet Connection!"` | Kart başlığı. |
| `cardMessageOffline` | `String` | `"Please Back Online"` | Kart açıklaması. Boş bırakılırsa gösterilmez. |
| `cardDelay` | `Long` | `2000` | Çubuk ile kart arasındaki bekleme (ms). |
| `cardBackgroundColor` | `Color` | `#311A1A` | Kart zemini. |
| `cardContentColor` | `Color` | `#E1D9D9` | Kartın içerik rengi; `navigationButton` içinde renk belirtmeyen bileşenler (ör. düz bir `Icon`) bunu kullanır. |
| `cardTextColor` | `Color` | Beyaz | Kart metni ve ikon rengi. |
| `dampingRatioBouncy` | `DampingRatioBouncy` | `MEDIUM_HIGH` | Kartın giriş yaylanması: `HIGH` (en hareketli), `MEDIUM_HIGH`, `MEDIUM`, `LOW` (en yumuşak). |
| `navigationButton` | `@Composable () -> Unit` | Boş | Kartın altına eklenecek buton, ör. ağ ayarlarını açmak için. |
| `content` | `@Composable () -> Unit` | Boş | Gözlenen ekranın içeriği. |

### Örnekler

```kotlin
// Sadece bildirim: içerik bulanıklaşmaz ve kullanılmaya devam eder.
AnimatedConnectivityBox(
    isConnected = isConnected,
    blurWhenOffline = false,
    overlayWhenOffline = false,
) { AppContent() }

// Hızlı tepki ve sakin bir kart.
AnimatedConnectivityBox(
    isConnected = isConnected,
    messageBarDelay = 250L,
    cardDelay = 650L,
    dampingRatioBouncy = DampingRatioBouncy.LOW,
) { AppContent() }
```

## Notlar

- **Durum kaydı:** Çubuk ve kartın görünürlüğü `rememberSaveable` ile tutulur; ekran döndürülünce animasyon baştan oynamaz.
- **Metinler:** Kütüphane metin kaynağı içermez; çok dilli uygulamalarda metinleri `stringResource` ile verin.
- **R8:** Kütüphane yansıma kullanmaz, ek keep kuralı gerektirmez.

## Lisans

GNU GPL v3. Ayrıntılar için `License` dosyasına bakın.
