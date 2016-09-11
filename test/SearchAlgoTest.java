package test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import search.Hw1VO;
import search.InputObjectFactory;
import search.SearchAlgo;

public class SearchAlgoTest {

	List<TestConfiguration> testList = new ArrayList<TestConfiguration>();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Properties testProperties = new Properties();
		InputStream reader = new FileInputStream("src/test/TestConfiguration.properties");
		testProperties.load(reader);
		
		Enumeration<?> e = testProperties.propertyNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			String value = testProperties.getProperty(key);
			String values[] = value.split(",");
			TestConfiguration testConfig = new TestConfiguration();
			testConfig.setInputFileName(values[0]);
			testConfig.setOutputFileName(values[1]);
			testList.add(testConfig);
		}
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
		for(int i=0;i<testList.size();i++){
			
			Hw1VO hw1;
			try {
				hw1 = InputObjectFactory.readFile(testList.get(i).getInputFileName());
			
			List<String> path = null;
			switch (hw1.getAlgo()){
			case "BFS": path = SearchAlgo.runBFS(hw1.getGraph(),hw1.getStartState(),hw1.getGoalState());
						break;
			case "DFS": path = SearchAlgo.runDFS(hw1.getGraph(),hw1.getStartState(),hw1.getGoalState());
						break;
			case "UCS":path =  SearchAlgo.runUCS(hw1.getGraph(),hw1.getStartState(),hw1.getGoalState());
						break;
			case "A*":path = SearchAlgo.runAstar(hw1.getGraph(),hw1.getHeuristicMap(), hw1.getStartState(),hw1.getGoalState());
						break;
				}
			
			String outputFile = testList.get(i).getOutputFileName();
			FileReader inpFile = new FileReader(outputFile);
		 	BufferedReader bufferedReader = 
	                new BufferedReader(inpFile);
		 	List<String> expectedPath = new ArrayList<String>();
		 	String exPath =null;
		 	
		 	while((exPath = bufferedReader.readLine())!=null){
		 		expectedPath.add(exPath);
		 	}
		 	
		 	boolean filesMisMatch = true;
		 	for(int j=path.size()-1, k=0;j>0&& k<expectedPath.size()&& path.size()==expectedPath.size();j--,k++){
		 		if(!expectedPath.get(k).equals(path.get(j))){
		 			filesMisMatch = true;
		 			break;
		 		}else{
		 			filesMisMatch = false;
		 		}
		 	}
		 	
		 	if(filesMisMatch){
		 		fail("Input File "+testList.get(i).getInputFileName());
		 		
		 	}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
	}

}
