
package linkedlisttest;

/*
 * LLRemovingTest
 * LL = Linked List
 * 
 *  We test list removing behaviors here.
 *  See "DynamicList.java" for the list
 *  labeled "Removing".
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

public class LLRemovingTest extends UnitTest {

	DynamicList<Thing> list; //testee	

	//--------------------------------------------------------------

	public List<Integer> listElementIds() {
		return Arrays.asList(100, 101, 102, 103, 104, 105, 106, 107, 108, 109);
	}

	public void beforeEach() {
		//Called before each test
		//Test setup
		//Construct our testee list ivar
		super.beforeEach();
		list = LinkedListFactory.newList();
		for (int eachId : listElementIds())
			list.add(new Thing(eachId));
	}

	public void afterEach() {
		//Called after each test
		//Test teardown / cleanup
		super.afterEach();
		list = null;
	}

	//--------------------------------------------------------------
	//Tests


    
    private void testRemoveFirst() {
        List<Integer> expectedIds = Arrays.asList(101, 102, 103, 104, 105, 106, 107, 108, 109), actualIds;
        list.removeFirst();
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }

    private void testRemoveLast() {
        List<Integer> expectedIds = Arrays.asList(100, 101, 102, 103, 104, 105, 106, 107, 108), actualIds;
        list.removeLast();
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }

    private void testRemoveAll() {
        List<Integer> expectedIds = Arrays.asList(), actualIds;
        list.removeAll();
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }

    private void testRemoveIndex() {
        List<Integer> expectedIds = Arrays.asList(100, 102, 103, 104, 105, 106, 107, 108, 109), actualIds;
        list.removeIndex(1);
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }

    private void testRemove() {
        List<Integer> expectedIds = Arrays.asList(100, 101, 102, 103, 104, 106, 107, 108, 109), actualIds;
        list.remove(i -> {return i.getId() > 104;});
		actualIds = map(list.toJavaList(), thing -> thing.getId());
		assertEquals(expectedIds, actualIds);
    }
	//--------------------------------------------------------------
	//Test Entry Point (Startup)

	public static void main(String[] args) {
		LLRemovingTest unitTest = new LLRemovingTest();
		UnitTestManager<LLRemovingTest> mgr;
		mgr = new UnitTestManager<>("LinkedList", unitTest, unitTest.subTests());
		mgr.runAll();
	}

	List<SubTest> subTests() {
		//Build the sub-tests
		List<SubTest> tests = new ArrayList<>();
		tests.add(newSubTest("testRemoveFirst", () -> this.testRemoveFirst()));
		tests.add(newSubTest("testRemoveLast", () -> this.testRemoveLast()));
		tests.add(newSubTest("testRemoveAll", () -> this.testRemoveAll()));
		tests.add(newSubTest("testRemoveIndex", () -> this.testRemoveIndex()));
		tests.add(newSubTest("testRemove", () -> this.testRemove()));



		// tests.add(newSubTest("testFind", () -> this.testFind()));


		return tests;
	}

}
