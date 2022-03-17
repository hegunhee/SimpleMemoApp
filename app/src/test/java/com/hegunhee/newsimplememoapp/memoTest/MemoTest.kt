package com.hegunhee.newsimplememoapp.memoTest

//open class MemoTest : KoinTest {
//
//    @get:Rule
//    val mockitoRule = MockitoJUnit.rule()
//
//    @Mock
//    private lateinit var context: Application
//
//
//    private val dispatcher = TestCoroutineDispatcher()
//
//    val viewModel: MemoTestViewModel by inject()
//
//    @Before
//    fun setUp() {
//        startKoin {
//            androidContext(context)
//            modules(memoTestModule)
//        }
//        Dispatchers.setMain(dispatcher)
//    }
//
//    @After
//    fun tearDown() {
//        stopKoin()
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `add Memo`() = runBlockingTest {
//        val memo = getOneMockingMemo()
//        val memos = listOf<Memo>(memo)
//        viewModel.addMemo(memo)
//        viewModel.getAllMemo()
//        Assert.assertEquals(viewModel.memos, memos)
//    }
//
//    @Test
//    fun `delete One Memo`() = runBlockingTest {
//        val memo = getOneMockingMemo()
//        val memos = listOf<Memo>()
//        viewModel.addMemo(memo)
//        viewModel.deleteMemo(memo)
//        viewModel.getAllMemo()
//        Assert.assertEquals(viewModel.memos, memos)
//    }
//
//    @Test
//    fun `delete other Memo`() = runBlockingTest {
//        val memos = getTwentyMockingMemo().filter { it.year == 2022}.filter { it.month == 3 }.toList()
//        viewModel.addMemos(getTwentyMockingMemo())
//        viewModel.getAllMemo()
//        Assert.assertEquals(viewModel.memos.filter { it.year == 2022 }.filter{it.month == 3 }.toList(), memos)
//    }
//
//    @Test
//    fun `delete all Memo`() = runBlockingTest {
//        val memos = listOf<Memo>()
//        viewModel.addMemos(getTwentyMockingMemo())
//        viewModel.deleteAllMemo()
//        viewModel.getAllMemo()
//        Assert.assertEquals(viewModel.memos,memos)
//    }
//
//    @Test
//    fun `delete other Memo by Repository`() = runBlockingTest {
//        val memos = getTwentyMockingMemo().filter { it.year == 2022}.filter { it.month == 3 }.sortedByDescending { it.day }.toList()
//        viewModel.addMemos(getTwentyMockingMemo())
//        viewModel.getMemoSortedByYearAndMonth(2022,3)
//        Assert.assertEquals(viewModel.memos, memos)
//    }
//
//}