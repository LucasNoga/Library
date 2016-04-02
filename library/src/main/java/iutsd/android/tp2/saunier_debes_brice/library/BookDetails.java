package iutsd.android.tp2.saunier_debes_brice.library;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BookDetails
    extends AppCompatActivity {
  private Book book;
  private TextView  bookNameView;
  private TextView  bookAuthorView;
  private TextView  bookDescriptionView;
  private ImageView bookCoverImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_details);

    Bundle extras = getIntent().getExtras();
    if (extras == null)
      return;

    book = (Book) extras.getSerializable("Book");

    bookNameView        = (TextView) findViewById(R.id.bookName);
    bookAuthorView      = (TextView) findViewById(R.id.bookAuthor);
    bookDescriptionView = (TextView) findViewById(R.id.bookDescription);
    bookCoverImageView  = (ImageView) findViewById(R.id.bookImage);


    bookNameView.setText(book.getBookName());
    bookAuthorView.setText(book.getAuthor());
    bookDescriptionView.setText(book.getDescription());
    if (book.getImageUri() != null)
      bookCoverImageView.setImageURI(Uri.parse(book.getImageUri()));

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_book_details, menu);
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

  public void onClickCloseButton(View v) {
    this.finish();
  }
}
