import java.util.*;    

public class SkipList { // By BHAVIKA :) for OS&DS (COMP-7212)
	
	static final int LIST_MAXIMUM = 1000 ;
	Node start ;
	Node end ;
	Scanner s = new Scanner(System.in);
	
	SkipList() {
		// constructor
		start = new Node(-1,3) ;
		end = new Node(LIST_MAXIMUM,0) ;
		for( int i=0 ; i<3 ; i++ )
			start.pointers[i] = end ;
		System.out.println("\n\tInitialised Skip list with START-NODE (-1) & END-NODE (LIST_MAXIMUM) !") ;
		display() ;
	}
	
	public int menu() {

		System.out.println("Press:\n1 to add node\n2 to remove node\n3 to add a key\n4 to remove a key\n5 to FIND a key in the SkipList\n AND 0 to exit the menu!") ;
		int ch = s.nextInt() ;
		switch(ch) {
		  case 1:
		    addNODE(); display(); 			break;
		  case 2:
		    removeNODE(); display();  	break;
		  case 3:
		    addKEY(); display();  			break;
		  case 4:
		    removeKEY(); display();  		break;
		  case 5:
		    findKEY(); 						break;
		  case 0:
			System.out.println("Thank you !\n\n\n SKIP-LIST by BHAVIKA KHARE") ; break;
		  default:
			System.out.println("Wrong input !") ;
		}
		return ch ;
		
	}
	
	public Node findPREDECESSOR( int id ) {		
		Node p = start ;
		int i = 2 ;
		while(i>=0) {
			while(p.pointers[i].address<LIST_MAXIMUM&&p.pointers[i].address<id)
				p = p.pointers[i] ;
			i--;
		}
		return p ;
	}
	
	public Node findPREDECESSORslow( int id ) {
		// helps display the routing of the search in real time !
		Node p = start ;
		int i = 2 ;
		while(i>=0) {
	        try {
	            Thread.sleep(LIST_MAXIMUM) ;
	        } catch (Exception e) {}
			System.out.println("Searching through LEVEL "+(i+1)) ;
			while(p.pointers[i].address<LIST_MAXIMUM&&p.pointers[i].address<id)
				p = p.pointers[i] ;
			p.show() ;
			i-- ;
		}
		return p ;
	}
	
	public boolean addNODE() {    
			
		System.out.print("Enter Node ID [ a multiple of 100 between 0 & 999 ] :\t") ;
		int a = s.nextInt();
		if(a<=-1||a>=LIST_MAXIMUM||a%100!=0) {
			System.out.println("Input rejected !") ;
			return false ;
		}
		Node node = new Node(a);
		
		Node p = start ;
		Node[] array = new Node[3] ;
		int i = 2 ;
		while(i>=0) {
			while(p.pointers[i].address<LIST_MAXIMUM&&p.pointers[i].address<a)
				p = p.pointers[i] ;
			array[i] = p ;
			i--;
		}
		
		if (array[0].pointers[0].address==a) {
			System.out.println("Can't insert duplicate !") ;
			return false ;
		} else {
			Node save ;
			int level = node.pointers.length ;
			
			for( int c = 0 ; c<level ; c++ ) {
				save = array[c].pointers[c] ;
				array[c].pointers[c] = node ;
				node.pointers[c] = save;
			}
			
		}
				
		return true;
	}
	
	public boolean removeNODE() {    
		
		System.out.print("Enter Node ID [ a multiple of 100 between 0 & 999 ] :\t") ;
		int a = s.nextInt();
		if(a<=-1||a>=LIST_MAXIMUM||a%100!=0) {
			System.out.println("Input rejected !") ;
			return false ;
		}
		
		Node p = start ;
		Node[] array = new Node[3] ;
		int i = 2 ;
		while(i>=0) {
			while(p.pointers[i].address<LIST_MAXIMUM&&p.pointers[i].address<a)
				p = p.pointers[i] ;
			array[i] = p ;
			i--;
		}
		
		if (array[0].address>a) {
			System.out.println("Can't delete a node that doesn't exist !") ;
			return false ;
		} else {
			Node node = array[0].pointers[0] ;
			int level = node.pointers.length ;
			array[0].keys.addAll(node.keys) ;
			for( int c = 0 ; c<level ; c++ ) {
				array[c].pointers[c] = node.pointers[c] ;
			}			
		}
				
		return true;
		
	}
	
	public boolean addKEY() {
		
		System.out.print("Enter Key ID [ between 0 & 999 and not a multiple of 100 ] :\t") ;
		int a = s.nextInt();
		if(a<=-1||a>=LIST_MAXIMUM||a%100==0) {
			System.out.println("Input rejected !") ;
			return false ;
		}
		findPREDECESSOR(a).keys.add(a) ;	
		return true;
		
	}
	
	public boolean removeKEY() {
		
		System.out.print("Enter Key ID [ between 0 & 999 and not a multiple of 100 ] :\t") ;
		int a = s.nextInt();
		if(a<=-1||a>=LIST_MAXIMUM||a%100==0) {
			System.out.println("Input rejected !") ;
			return false ;
		}
		
		Node prenode = findPREDECESSOR(a) ;
		int index = prenode.keys.indexOf(a) ;
		if(index==-1) {
			System.out.println("Key not found !") ;
			return false ;
		} else { 
			prenode.keys.remove(index) ;
			return true ;
		}
		
	}
	
	public boolean findKEY() {
		
		System.out.print("Enter Key ID [ between 0 & 999 and not a multiple of 100 ] :\t") ;
		int a = s.nextInt();
		if(a<=-1||a>=LIST_MAXIMUM||a%100==0) {
			System.out.println("Input rejected !") ;
			return false ;
		}

		System.out.println("---SearchingTheSkipList---\nStarting at START-NODE(-1)") ;
		start.show() ;
		int index = findPREDECESSORslow(a).keys.indexOf(a) ;
		if(index==-1) {
			System.out.println("Key not found !\n--------------------------") ;
			return false ;
		} else { 
			System.out.println("Key found @index"+(index+1)+"\n--------------------------") ;
			return true ;
		}
		
	}
	
	// display function
	
	public void display() {
		System.out.println("---TheSkipList---") ;
		Node p = start ;
		while(p.address<LIST_MAXIMUM) {
			p.show() ;
			p = p.pointers[0] ;
		}	p.show();
		System.out.println("-----------------") ;
	}
	
	class Node {
		
		// nested class inside SkipList for each node
		
		public int address ;
		public Node[] pointers ;
		public ArrayList<Integer> keys ;
		
		// constructors
		
		Node( int a , int l ) {
			// fixed length constructor for START & END NODES
			address = a ;
			pointers = new Node[l]; 
			keys = new ArrayList<Integer>();
		}
		
		Node( int a ) {
			Random r = new Random();
			address = a ;			
			int l = r.nextInt(3) +1 ;
			pointers = new Node[l]; 
			keys = new ArrayList<Integer>();
		}
		
		// display function
		
		public void show() {
			System.out.print(" "+address+"\t level"+pointers.length+"\t") ;
			keys.forEach( (k)->System.out.print(k+" ") ) ;
			System.out.println() ;
		}
		
	}

	public static void main(String[] args) {
		
		int ch=1;
		SkipList SL = new SkipList() ;
			// fill SkipList
		for( int i=0 ; i<5 ; i++ )
			SL.addNODE() ;
		for( int i=0 ; i<10 ; i++ )
			SL.addKEY() ;
		System.out.print("\n A skip-list has been CREATED! Here is your skip-list :\n") ;
		SL.display() ;
		System.out.print("\n") ;
			// let the user play around
		do {
			ch = SL.menu() ;
		} while(ch!=0);

	}

}
