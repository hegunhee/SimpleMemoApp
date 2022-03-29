# New 덜 편한 가계부
## 소개  
기존에 했었던 덜 편한 가계부에서 데이터바인딩, Unit Test등의 새로운 기술들을 사용해  
다시 만들어 봤습니다.    
## App 화면  

## 프로젝트 구조  
MVVM을 지향하며 DataBinding을 사용하였습니다.    
## 앱 사진  
![memoView](https://user-images.githubusercontent.com/57277631/160281890-b0bc213d-128e-441f-80d8-1c6c37e12245.PNG)  
시작할때, 가계부 화면  
![addMemoView](https://user-images.githubusercontent.com/57277631/160281895-6e1661ae-5b0c-4e09-a58e-09a6b11fcf09.PNG)  
가계부 추가, FloatingActionButton 클릭시 이동  
![detailMemoView](https://user-images.githubusercontent.com/57277631/160281897-da526653-76e4-4a56-8530-3d3ff4c39f61.PNG)  
가계부 수정 및 삭제, 클릭시 이동  
## 사용된 라이브러리  
* Architecture
  - ViewModel  
  - LiveData  
* UI
  - ConstraintLayout  
  - RecyclerView  
  - Fragment  
* Third Party  
  - Kotlin Coroutine
  - Koin  
  - Room  
## 기술 정보  
  * **Android Clean Architecture** 적용  
    기존에 안드로이드를 공부할때는 모든 활동을 onCreate안에 구현했지만  
    AAC를 공부하고나서 로직 분리와 의존성 분리의 중요성을 깨닫고 프로젝트에 적용하였습니다.  
  * **Room DataBase** 적용  
    가계부라는 특성상 가계부 데이터를 저장하고 불러오는 과정이 필요한데  
    Room을 이용해서 데이터베이스 생성, 테이블 관리, DAO(Data Access Object)를 쉽게 관리하였습니다.
  * **Coroutine** 적용  
    Room을 사용하거나 가계부 정보들을 정렬하는데 많은 시간이 필요하기때문에  
    Coroutine을 사용해서 main Thread의 부담을 줄일 수 있음
  * **Koin** 적용  
    Activity에서 ViewModel을 사용할때 의존성이나 Model의 Repository를 사용할때  
    의존성 주입을 위해 Koin을 사용하였습니다.  
    러닝커브가 낮고 사용하기 쉬웠습니다.  
## 진척도  
1) 가계부에 관한 Test Code 작성  
2) Test Code에 Koin 도입 및 작동하는지 확인  
3) Room에서 데이터를 불러올때 연도별, 월별로 정렬된 데이터 호출  
5) BottomNavigationView 도입  
6) MemoViewModel 및 Repository 코드 작성 및 MemoFragment UI 작성  
7) MemoViewModel DataBinding 도입  
8) Adapter 및 LiveData Observe 사용  
9) DetailMemoActivity작성  
10) DetailMemoActivity로직까지 완성  
----------------완료-------------------  
11) 통계Fragment xml 작성  
12) 통계Fragment 로직 작성  
13) 설정 및 기타사항 추가  
## 이후 개선될 사항  
현재 계획으로는 데이터 추가, 확인, 지출, 수입별 통계까지이지만 더 추가 예정  
현재 Fragment가 초기화되지않아 추가, 수정시 Adapter에 드러나지않음 향후 방법을 모색해야됨 -> Fragment의 onResume 생명주기를 활용  
## 변경사항  
원래는 RecyclerView에 데이터 변경이 일어날때마다 BindingAdpater로 변경하려고 했지만 계속 실패해 데이터가 변경된것을 적용할때  
LiveData를 이용해 Fragment에서 LiveData를 옵저빙해서 데이터를 변경해줌  
Fragment의 onResume 생명주기를 활용해서 데이터의 변화를 확실하게 보여줌  
