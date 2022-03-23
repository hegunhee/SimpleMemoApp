package com.hegunhee.newsimplememoapp



//class TestKoin : KoinTest {
//
//    @get:Rule
//    val mockitoRule = MockitoJUnit.rule()
//    @Mock
//    private lateinit var context : Application
//
//    private val dispatcher = TestCoroutineDispatcher()
//
//    private val addUsecase : AddPeopleUseCase by inject()
//    private val getAllUsecase : GetAllPeopleUseCase by inject()
//    private val sayHelloUseCase : SayHelloUseCase by inject()
//
//    @Before
//    fun setUp(){
//        startKoin {
//            androidContext(context)
//            modules(koinTestModule)
//        }
//        Dispatchers.setMain(dispatcher)
//    }
//
//    @After
//    fun tearDown(){
//        stopKoin()
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun hello() = runBlockingTest{
//        assertEquals(sayHelloUseCase(),"Hello")
//    }
//
//    @Test
//    fun `add and get data`() = runBlockingTest {
//        val peoples = List<People>(5){ People("이름${it}",it) }
//        for(i in 0 until 5){
//            addUsecase(peoples[i])
//        }
//        Assert.assertEquals(peoples, getAllUsecase())
//    }
//}