# 덜 편한 가계부  
## 2022.03.04 ~ 2022.04.16  
## 소개  
"편한 가계부" 앱을 모티브로해서 만들었습니다.  
가계부를 추가, 수정, 삭제를 할 수 있습니다.  
통계 탭을통해 이번달에 어떤 항목(식비,교통,기타)으로 얼마를 사용했는지 볼 수 있습니다.  


## 사용된 라이브러리  
* JetPack
  - AAC-ViewModel
  - Room
  - Compose - View기반 앱, Compose 기반 앱 총 2가지가 있음
* UI
  - ConstraintLayout  
  - RecyclerView  
  - Fragment  
* Kotlin
  - Coroutine Flows  
## 기술 정보  
  * **AAC-ViewModel** 적용  
    Activity나 Fragment 코드가 비대해지는것을 막고 데이터를 관리하는 데 사용합니다.  
    액티비티가 프래그먼트가 화면 회전 등의 이유로 Destroy 상태가 되어도 AAC-ViewModel은 Destroy 되지않기때문에  
    데이터를 쉽게 관리할 수 있습니다. 그리고 ViewModelScope를 사용해 Coroutine을 쉽게 사용할 수 있습니다.
    
  * **Coroutine Flows** 적용  
    DataBinding으로 xml과 Activity(Fragment)를 결합시킬때 Flows를 사용해 데이터를 쉽게 연동하였습니다.

  * **DataBinding** 적용  
    dataBinding을 사용하여 findViewById를 사용하지 않으며 xml 파일과 데이터를 연결해줍니다.  
    그 이외에도 데이터바인딩 변수를 viewModel로 만들어 viewModel에서 만든 함수를 Button의 onClick메소드로 사용할 수 있습니다.  
    Listener 에서 Context를 사용해야하는경우는 ViewModel에 Context를 할당하면 안되기때문에  
    어쩔 수 없이 Activity나 Fragment에서 구현하였습니다.
    
  * **Room DataBase** 적용  
    가계부라는 특성상 가계부 데이터를 저장하고 불러오는 과정이 필요한데  
    Room을 이용해서 데이터베이스 생성, 테이블 관리, DAO(Data Access Object)를 쉽게 관리하였습니다.
    
  * **Coroutine** 적용  
    Room을 사용하거나 가계부 정보들을 정렬하는데 많은 시간이 필요하기때문에  
    Coroutine을 사용해서 main Thread의 부담을 줄일 수 있음
    
  * **Hilt** 적용  
    프로젝트의 크기가 커질경우에 Koin은 불편한점이 많기때문에 Koin을 Hilt로 migration하였습니다.  
    의존성 주입을 위해 사용했습니다.  
    Android를 위한 컴포넌트 제공하므로 보다 쉽게 사용
    
  * **Jetpack Compose** 적용
    View기반 앱과 Jetpack compose로 만든 앱이 총 2가지
    선언형 UI로 보다 직관적인 UI 코드 작성, 코드 재사용률이 올라감
    

## 앱 사진  
![memoView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/bb3cd95c-8b07-4863-94c4-0d85f354d5a3)  
시작할때, 가계부 화면  
![detailMemoView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/cc188532-0357-4cfa-a189-5a0af26f3e2d)  
가계부 추가, FloatingActionButton 클릭시 이동  
![staticsMemoView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/e64fb321-9e39-4b70-8aab-7db9fba5a0da)  
통계 탭  
![detailStaticsView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/106ed6da-742c-4dcd-9f63-63840ebb7c8a)  
통계 자세히보기 탭  
![DateSelectorView](https://github.com/hegunhee/NewSimpleMemoApp/assets/57277631/d4ac22aa-3bbb-44ec-abb6-769ed4a84e96)  
날짜 선택

