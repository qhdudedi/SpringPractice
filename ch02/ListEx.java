package springstdy;
import java.util.ArrayList;
import java.util.List;
public class ListEx {

	public static void main(String[] args) {
		List<String> names = new ArrayList();
		
		//String타입의 데이터 넣기
		names.add("홍");
		names.add("김");
		names.add("James");
		
		//for문으로 데이터 출력
		for(String name : names) {
			System.out.println(name);
		}

	}
}
