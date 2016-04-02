package iutsd.android.tp2.saunier_debes_brice.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity
    extends AppCompatActivity implements BooksListFragment.BooksListProvider,
                                         BooksListFragment.OnListFragmentInteractionListener{

  private static Fragment fragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    fragment = new BooksListFragment();

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
    return Book.GET_TEST_LIST();
  }

  @Override
  public void onClickBookDetails(Book book) {
    Intent bookDetails = new Intent(this, BookDetails.class);

    bookDetails.putExtra("Book", book);
    startActivity(bookDetails);

  }

  @Override
  public void onClickBookModify(Book book) {

  }

  @Override
  public void onClickBookActions(Book book) {

  }
}
