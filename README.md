# 덜 편한 가계부  
## 2022.03.04 ~ 2022.04.16    
추후 View기반 코드를 걷어낼 예정(현재 2025.04.18 기준 작업할 계획 없음)    
## 소개  
"편한 가계부" 앱을 모티브로해서 만들었습니다.  
가계부를 추가, 수정, 삭제를 할 수 있습니다.  
통계 탭을통해 이번달에 어떤 항목(식비,교통,기타)으로 얼마를 사용했는지 볼 수 있습니다.  

## 개발 환경  
Kotlin : 1.8.0  
Java : 17  
gradle : 8.0.0  
AGP : 7.4.2  
IDE : Android Studio Ladybug (2024.2.1 Patch 2)  

## 모듈 구조   
📦build-logic  
📦app  
📦feature (Compose)  
┣ 📂category  
┣ 📂memo  
┣ 📂statics  
📦core  
┣ 📂data  
┣ 📂designsystem  
┣ 📂domain  
┣ 📂ui  
┗ 📂util  

## 기술 스택
- network
  - Retrofit2, kotlinx.serialization, Corouitne  
- UI
  - Jetpack Compose, AAC-ViewModel, Coroutine Flows
- DI
  - Hilt
- Test
  - Junit4, mockito-kotlin, Espresso, MockWebServer
## 기술 정보  
  * **AAC-ViewModel** 적용  
    Activity나 Fragment 코드가 비대해지는것을 막고 데이터를 관리하는 데 사용합니다.  
    액티비티나 프래그먼트가 화면 회전 등의 이유로 Destroy 상태가 되어도 AAC-ViewModel은 Destroy 되지않기때문에  
    데이터를 쉽게 관리할 수 있습니다. 그리고 ViewModelScope를 사용해 Coroutine을 쉽게 사용할 수 있습니다.
    
  * **Coroutine Flows** 적용  
    상태를 관리하고 옵저버블하게 사용하므로 Composable 함수에서 쉽게 사용 가능
    용도에 따라 SharedFlow, StateFlow중 선택 가능  
    
  * **Hilt** 적용
    기존에는 Koin을 사용했으나 에러가 런타임에 발생하고 프로젝트가 확장될경우 의존성을 확인하기 어렵기때문에
    Koin에서 Hilt로 이전했습니다.  
    Android를 위한 컴포넌트 제공하므로 보다 쉽게 사용
    
  * **Jetpack Compose** 적용  
    View기반 앱과 Jetpack compose로 만든 앱이 총 2가지
    선언형 UI로 보다 직관적인 UI 코드 작성, 코드 재사용률이 올라감
    

## 앱 사진  
| 메인 화면 | 가계부 추가 | 통계 탭 |
| ------ | -------- | ------ |
| ![memoView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/bb3cd95c-8b07-4863-94c4-0d85f354d5a3) | ![detailMemoView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/cc188532-0357-4cfa-a189-5a0af26f3e2d) |  ![staticsMemoView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/e64fb321-9e39-4b70-8aab-7db9fba5a0da) |
| 통계 자세히보기 | 날짜 선택 |     |
| ![detailStaticsView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/106ed6da-742c-4dcd-9f63-63840ebb7c8a) | ![DateSelectorView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/d4ac22aa-3bbb-44ec-abb6-769ed4a84e96) |
