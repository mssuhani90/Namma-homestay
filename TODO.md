# Namma-HomeStay Implementation TODO

## Completed
- [x] Project structure and Gradle files
- [x] MainActivity and basic Compose UI (HomeScreen)
- [x] Basic features: Image picker, menu update UI
- [x] Firebase dependencies
- [x] Repository class

## In Progress (Production Heavy)
- [ ] Production hardening: enable R8/shrinking for release
- [ ] Production hardening: fill Proguard rules safely for Compose/Firebase
- [ ] Architecture: add Navigation Compose with proper NavHost
- [ ] Architecture: add ViewModel + StateFlow for screen states
- [ ] UI: refactor HomeScreen into screens (Home/Menu/Images)
- [ ] Firebase: implement profile save + load
- [ ] Firebase: implement Firebase Storage image upload + save URLs
- [ ] Firebase: implement menu save + load
- [ ] Firebase: implement inquiry & local guide placeholders (data model hooks)
- [ ] Auth: add FirebaseAuth sign-in/out + current user gate
- [ ] UX: add loading/error UI + disable actions during requests
- [ ] Observability: add Crashlytics + Performance Monitoring

## Next Steps
- [ ] Test: assembleRelease + bundleRelease
- [ ] Test on device: image upload + menu update flows

