import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider{

  //max pages to search for
  private static final int MAX_PAGES = 10;

  /*
  By choosing the right datastructures, we can ensure that
  URLs that are visited are unique and that the URLs to be
  visited are easily appended
  */
  private Set<String> pagesVisited = new HashSet<String>();
  private List<String> pagesToVisit = new LinkedList<String>();


  /*
  Helper Method
  If the pageVisited Set contains the URL that was taken
  from the pageToVisit List, the it traverses to the next list
  as it removes the current one and compares again. It stops
  when the pageToVisit link doesnt exist in the pageVisited
  */
  private String nextUrl(){
    String nextUrl;
    do{
      nextUrl = this.pagesToVisit.remove(0);
    }while(this.pagesVisited.contains(nextUrl));
    this.pagesVisited.add(nextUrl);
    return nextUrl;
  }


  /*
    Search Method
    Creates a "SpiderLeg" object to crawl through the pages.
    The leg crawls the currentUrl and gets the entire document and
    searches for the keyword specified and sets the variable successs to true/false
  */
  public void search(String url, String searchWord){
    String currentUrl;
    while(this.pagesVisited.size() < MAX_PAGES){
      SpiderLeg leg = new SpiderLeg();
      if(this.pagesToVisit.isEmpty()){
        currentUrl = url;
        this.pagesVisited.add(url);
      }else{
        currentUrl = this.nextUrl();
      }

    leg.crawl(currentUrl);
    boolean success = leg.searchForWord(searchWord);
    if(success){
      System.out.println(String.format("Success: Word %s found at %s",searchWord,currentUrl));
      break;
    }
    this.pagesToVisit.addAll(leg.getLinks());
    System.out.println("\n**Done** Visited " + this.pagesToVisit.size() + " web page(s)");
    }
  }
}
