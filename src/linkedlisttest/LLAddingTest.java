package linkedlisttest;
/*
 * LLAddingTest
 * LL = Linked List
 * 
 *  We test list adding behaviors here.
 *  See "DynamicList.java" for the list
 *  labeled "Adding".
 *  
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import linearpub.DynamicList;
import linearpub.LinkedListFactory;
import testutil.SubTest;
import testutil.Thing;
import testutil.UnitTest;
import testutil.UnitTestManager;
import static testutil.TestTool.map;

public class LLAddingTest extends UnitTest {
    DynamicList<Thing> list;

	public List<Integer> listElementIds() {
		return Arrays.asList(100, 101, 102, 103, 104, 105, 106, 107, 108, 109);
	}

    public void beforeEach() {
        super.beforeEach();
        list = LinkedListFactory.newList();
        for (int eachId : listElementIds()) {
            list.add(new Thing(eachId));
        }
    }

    public void afterEach() {
        super.afterEach();
        list = null;
    }

    //Tests
        
    private void testAddFirst() {
        List<Integer> expectedIds = Arrays.asList(99,100, 101, 102, 103, 104, 105, 106, 107, 108, 109), actualIds;
        list.addFirst(new Thing(99));
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }

    private void testAddLast() {
        List<Integer> expectedIds = Arrays.asList(100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110), actualIds;
        list.addLast(new Thing(110));
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }


    private void testAdd() {
        List<Integer> expectedIds = Arrays.asList(100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110), actualIds;
        list.add(new Thing(110));
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }

    private void testInsert() {
        //middle position
         List<Integer> expectedIds = Arrays.asList(100, 101, 102, 103, 77, 104, 105, 106, 107, 108, 109), actualIds;
         list.insert(4, new Thing(77));
        // last position
        // List<Integer> expectedIds = Arrays.asList(100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 77), actualIds;
        // list.insert(10, new Thing(77));
        // first position
        // List<Integer> expectedIds = Arrays.asList(77, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109), actualIds;
        // list.insert(0, new Thing(77));
        // test exception
//        List<Integer> expectedIds = Arrays.asList(100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 77), actualIds;
//        list.insert(11, new Thing(77));
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }

    public static void main(String[] args) {
        LLAddingTest unitTest = new LLAddingTest();
        UnitTestManager<LLAddingTest> mgr;
        mgr = new UnitTestManager<>("LinkedList", unitTest, unitTest.subTests());
        mgr.runAll();
    }

    private List<SubTest> subTests() {
        List<SubTest> tests = new ArrayList<>();
        tests.add(newSubTest("testAddFirst", () -> this.testAddFirst()));
        tests.add(newSubTest("testAddLast", () -> this.testAddLast()));
        tests.add(newSubTest("testAdd", () -> this.testAdd()));
        tests.add(newSubTest("testInsert", () -> this.testInsert()));



		// tests.add(newSubTest("testFind", () -> this.testFind()));
        return tests;
    }
    
}
