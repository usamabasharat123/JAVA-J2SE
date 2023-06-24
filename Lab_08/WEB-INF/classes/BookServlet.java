import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

class Book {
    String title;
    String author;
    String ISBN;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }
}

public class BookServlet extends HttpServlet {
    ArrayList<Book> arrayList;

    public void init(ServletConfig scfg) {
        arrayList = new ArrayList<Book>();
        arrayList.add(new Book("Java how to program", "Deitel", "9780136053064"));
        arrayList.add(new Book("Head First C#", "Andrew Stellman", "9781491976708"));
        arrayList.add(new Book("C#", "Svetlin Nakov", "9789544007737"));
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Record Room</title></head>");
        out.println("<body>");
        out.println("<h1>Welcome to the Books Library</h1>");
        out.println("<table><tr><th>Author name</th><th>Title name</th><th>ISBNname</th></tr>");
        for (Book book : arrayList) {
            out.println("<tr><td>" + book.author + "</td><td>" + book.title + "</td><td>"
                    + book.ISBN + "</td></tr>");
        }
        out.print("</table>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pr = response.getWriter();
        String operation = request.getParameter("command");

        switch (operation) {
            case "EnterRecord":
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                String isbn = request.getParameter("isbn");

                Book obj = new Book(title, author, isbn);
                arrayList.add(obj);

                pr.println("<html><body><h3>Record Inserted Successfully!</h3></body></html>");
                break;

            case "DeleteRecord":
                String isbnToDelete = request.getParameter("isbn");
                boolean recordFound = false;
                Iterator<Book> iterator = arrayList.iterator();
                while (iterator.hasNext()) {
                    Book book = iterator.next();
                    if (book.ISBN.equals(isbnToDelete)) {
                        iterator.remove();
                        recordFound = true;
                        break;
                    }
                }
                if (recordFound) {
                    pr.println("<html><body><h3>Record is Removed</h3></body></html>");
                } else {
                    pr.println("<html><body><h3>Record Not Found</h3></body></html>");
                }
                break;

            case "UpdateRecord":
                String titleToUpdate = request.getParameter("title");
                String authorToUpdate = request.getParameter("author");
                String isbnToUpdate = request.getParameter("isbn");

                boolean updateFound = false;
                for (Book book : arrayList) {
                    if (book.ISBN.equals(isbnToUpdate)) {
                        book.title = titleToUpdate;
                        book.author = authorToUpdate;
                        updateFound = true;
                        break;
                    }
                }

                if (updateFound) {
                    pr.println("<html><body><h3>Record is Updated!</h3></body></html>");
                } else {
                    pr.println("<html><body><h3>Record Not Found to be Updated</h3></body></html>");
                }
                break;
        }
    }
}
