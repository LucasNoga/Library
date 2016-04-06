package iutsd.android.tp2.saunier_debes_brice.library;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static iutsd.android.tp2.saunier_debes_brice.library.MainActivity.*;

public class ModifyBook
    extends AppCompatActivity {
  private static final int PICK_IMAGE_REQUEST = 3;
  private Book book;
  private String action = "";
  private TextView    bookNameView;
  private TextView    bookAuthorView;
  private TextView    bookDescriptionView;
  private ImageButton bookCoverImageButton;
  private Button      saveButton;
  private Bundle      extras;
  private String      selectedImgUri;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_modify_book);

    selectedImgUri = "";

    setExtras();

    if (extras == null)
      return;

    instantiateViews();

    fillFields();

  }

  private void setExtras() {
    extras = getIntent().getExtras();

    if (extras == null)
      return;

    if (getIntent().hasExtra("Book"))
      book = (Book) extras.getSerializable("Book");
    else
      book = new Book();

    action = extras.getString("Action");
  }

  private void instantiateViews() {
    bookNameView = (TextView) findViewById(R.id.bookName);
    bookAuthorView = (TextView) findViewById(R.id.bookAuthor);
    bookDescriptionView = (TextView) findViewById(R.id.bookDescription);
    bookCoverImageButton = (ImageButton) findViewById(R.id.bookImageButton);
    saveButton = (Button) findViewById(R.id.saveButton);
  }

  private void fillFields() {
    if (isActionModify()) {
      bookNameView.setText(book.getBookName());
      bookAuthorView.setText(book.getAuthor());
      bookDescriptionView.setText(book.getDescription());
      saveButton.setText("Modify");
      if (book.getImageUri() == null)
        bookCoverImageButton.setImageResource(R.drawable.livre_recherche_loupe);
      else
        bookCoverImageButton.setImageURI(Uri.parse(book.getImageUri()));
    } else if (isActionAddNewBook())
      saveButton.setText("Add");
  }

  private boolean isActionAddNewBook() {
    return action.equals(ACTION_ADD_NEW_BOOK);
  }

  private boolean isActionModify() {
    return action.equals(ACTION_MODIFY_EXISTING_BOOK);
  }

  public void onClickSaveButton(View v) {

    book.setAuthor(bookAuthorView.getText().toString());
    book.setBookName(bookNameView.getText().toString());
    book.setDescription(bookDescriptionView.getText().toString());
    if (!selectedImgUri.isEmpty())
      book.setImageUri(selectedImgUri);
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

  @TargetApi(Build.VERSION_CODES.KITKAT)
  public void onClickImage(View v) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("image/*");

    if (intent.resolveActivity(getPackageManager()) != null)
      startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null
        && data.getData() != null) {
      this.selectedImgUri = data.getDataString();
      bookCoverImageButton.setImageURI(Uri.parse(selectedImgUri));
    }
  }

  @Override
  public void finish() {
    Intent data = new Intent();

    data.putExtra("Book", book);
    if (isActionModify())
      setResult(RESULT_MODIFY_BOOK_OK, data);
    else if (isActionAddNewBook() && !isBookEmpty())
      setResult(RESULT_ADD_BOOK_OK, data);

    super.finish();
  }

  private boolean isBookEmpty() {
    return book.getAuthor() == null && book.getBookName() == null && book.getDescription() == null;
  }
}
