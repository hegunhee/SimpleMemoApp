# New 덜 편한 가계부
## 소개  
기존에 했었던 덜 편한 가계부에서 데이터바인딩, Unit Test등의 새로운 기술들을 사용해  
다시 만들어 봤습니다.    
## App 화면  

## 프로젝트 구조  
MVVM + DataBinding  
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
## 이후 개선될 사항  
현재 계획으로는 데이터 추가, 확인, 지출, 수입별 통계까지이지만 더 추가 예정  
현재 Fragment가 초기화되지않아 추가, 수정, 삭제시 Adapter에 드러나지않음 향후 방법을 모색해야됨  
## 변경사항  
원래는 RecyclerView에 데이터 변경이 일어날때마다 BindingAdpater로 변경하려고 했지만 계속 실패해 데이터가 변경된것을 적용할때  
LiveData를 이용해서 Fragment에서 옵저빙해서 데이터를 변경해줌  
