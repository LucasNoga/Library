package iutsd.android.tp2.saunier_debes_brice.library;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static iutsd.android.tp2.saunier_debes_brice.library.MainActivity.*;

public class ModifyBook
    extends AppCompatActivity {
  private Book book;
  private String action = "";
  private TextView  bookNameView;
  private TextView  bookAuthorView;
  private TextView  bookDescriptionView;
  private ImageView bookCoverImageView;
  private Button    saveButton;
  private Bundle    extras;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_modify_book);


    setExtras();

    if (extras == null)
      return;

    setViews();

    fillFields();

  }

  private void setExtras() {
    extras = getIntent().getExtras();

    if (extras == null)
      return;

    book = (Book) extras.getSerializable("Book");
    action = extras.getString("Action");
  }

  private void setViews() {
    bookNameView = (TextView) findViewById(R.id.bookName);
    bookAuthorView = (TextView) findViewById(R.id.bookAuthor);
    bookDescriptionView = (TextView) findViewById(R.id.bookDescription);
    bookCoverImageView = (ImageView) findViewById(R.id.bookImage);
    saveButton = (Button) findViewById(R.id.saveButton);
  }

  private void fillFields() {
    if (isActionModify()) {
      bookNameView.setText(book.getBookName());
      bookAuthorView.setText(book.getAuthor());
      bookDescriptionView.setText(book.getDescription());
      saveButton.setText("Modify");
      if (book.getImageUri() != null)
        bookCoverImageView.setImageURI(Uri.parse(book.getImageUri()));
    } else
      saveButton.setText("Add");



  }

  private boolean isActionModify() {
    return action.equals(ACTION_MODIFY_EXISTING_BOOK);
  }

  public void onClickSaveButton(View v) {
    book.setAuthor(bookAuthorView.getText().toString());
    book.setBookName(bookNameView.getText().toString());
    book.setDescription(bookDescriptionView.getText().toString());
    //book.setImageUri(bookAuthorView.getText().toString());

    this.finish();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_modify_book, menu);
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
  public void finish() {
    Intent data = new Intent();

    data.putExtra("Book", book);
    if (isActionModify())
      setResult(RESULT_MODIFY_BOOK_OK, data);

    super.finish();
  }
}
