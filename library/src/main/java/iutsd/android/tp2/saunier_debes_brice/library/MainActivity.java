package iutsd.android.tp2.saunier_debes_brice.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity
    extends AppCompatActivity
    implements BooksListFragment.BooksListProvider,
               BooksListFragment.OnListFragmentInteractionListener {

  public static final String ACTION_MODIFY_EXISTING_BOOK          = "modify";
  public static final String ACTION_ADD_NEW_BOOK                  = "add";
  public static final int    REQUEST_MODIFY_BOOK_CODE             = 1;
  public static final int    REQUEST_MODIFY_BOOK_COVER_IMAGE_CODE = 2;
  public static final int    RESULT_MODIFY_BOOK_OK                = 1;
  public static final int    RESULT_ADD_BOOK_OK                   = 2;
  BooksListFragment listFragmentInstance;
  private List<Book> bookList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    bookList = Book.GET_TEST_LIST();
   setContentView(R.layout.activity_main);

    listFragmentInstance =
        (BooksListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    listFragmentInstance.update();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public List<Book> getBooksList() {
    return bookList;
  }

  @Override
  public void onClickBookDetails(Book book) {
    Intent bookDetailsData = new Intent(this, BookDetails.class);

    bookDetailsData.putExtra("Book", book);

    startActivity(bookDetailsData);

  }

  @Override
  public void onClickBookModify(Book book) {
    Intent modifyBookData = new Intent(this, ModifyBook.class);

    modifyBookData.putExtra("Book", book);
    modifyBookData.putExtra("Action", ACTION_MODIFY_EXISTING_BOOK);

    startActivityForResult(modifyBookData, REQUEST_MODIFY_BOOK_CODE);
  }

  @Override
  public void onClickBookActions(Book book) {
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_MODIFY_BOOK_CODE) {
      if (data != null && data.hasExtra("Book")) {
        Book bookCopy = (Book) data.getSerializableExtra("Book");

        if (resultCode == RESULT_MODIFY_BOOK_OK) {
          for (Book book : bookList) {
            if (bookCopy.equals(book))
              book.updateFromAnotherBookCopy(bookCopy);
          }
        } else if (resultCode == RESULT_ADD_BOOK_OK)
          bookList.add(bookCopy);

        listFragmentInstance.update();
      }
    }
  }

  public void onClickAddButton(MenuItem item) {
    Intent modifyBookData = new Intent(this, ModifyBook.class);

    modifyBookData.putExtra("Action", ACTION_ADD_NEW_BOOK);

    startActivityForResult(modifyBookData, REQUEST_MODIFY_BOOK_CODE);
  }

  public void onClickHelpButton(MenuItem item) {
  }

  public void onClickSortByNameButton(MenuItem item) {
    listFragmentInstance.sortList(Book.SORT_ENUM.NAME);
    listFragmentInstance.update();
  }

  public void onClickSortByIDButton(MenuItem item) {
    listFragmentInstance.sortList(Book.SORT_ENUM.ID);
    listFragmentInstance.update();
  }
}
