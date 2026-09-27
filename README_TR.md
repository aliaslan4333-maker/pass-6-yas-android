# PASS 6 Yaş Tarama – Android

Bu depo, yüklenen PASS Temelli Eğitimsel Tarama HTML dosyasını çevrimdışı çalışan Android WebView uygulaması olarak paketler.

## Otomatik derleme
GitHub > Actions > Android Build > Run workflow ile derleme başlatılabilir.

Derleme iki çıktı üretir:
- Test için APK
- Play Store için release AAB

Not: Play Store'a gönderilecek AAB'nin imzalı olması gerekir. Bunun için GitHub Actions Secrets altında ANDROID_KEYSTORE_BASE64, ANDROID_KEYSTORE_PASSWORD, ANDROID_KEY_ALIAS ve ANDROID_KEY_PASSWORD tanımlanmalıdır.

Uygulama internet izni istemez ve form kayıtlarını WebView localStorage içinde cihazda tutar.
