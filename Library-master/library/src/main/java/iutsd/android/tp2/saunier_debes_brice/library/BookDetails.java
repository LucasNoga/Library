package iutsd.android.tp2.saunier_debes_brice.library;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/*Activité qui permet de lorsqu'on selectionne un livre de nous retourné ces caractéristique*/
public class BookDetails extends AppCompatActivity {
  private Book book;
  private TextView  bookNameView;
  private TextView  bookAuthorView;
  private TextView  bookDescriptionView;
  private ImageView bookCoverImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_details);

    Bundle extras = getIntent().getExtras(); /*Permet de recuperer les données qui ont été envoyées a cette activité*/
    if (extras == null)                      /*Si les données envoyés sont nulles on ne fait rien*/
      return;

    book = (Book) extras.getSerializable("Book"); /*recupere dans l'objet book les doonées qui ont été envoyés à l'intent*/

    //recupere les vues de l'activité
    bookNameView        = (TextView) findViewById(R.id.bookName); 
    bookAuthorView      = (TextView) findViewById(R.id.bookAuthor); 
    bookDescriptionView = (TextView) findViewById(R.id.bookDescription); 
    bookCoverImageView  = (ImageView) findViewById(R.id.bookImage); 


    bookNameView.setText(book.getBookName()); /*affiche le nom du livre selectionné dans un TextView*/
    bookAuthorView.setText(book.getAuthor()); /*affiche le nom de l'auteur du livre selectionné dans un TextView*/
    bookDescriptionView.setText(book.getDescription()); /*affiche la description du livre selectionné dans un TextView*/
    if (book.getImageUri() != null)
      bookCoverImageView.setImageURI(Uri.parse(book.getImageUri())); /* Si le livre possede une image alors on l'affiche l'image du livre selectionné dans un ImageView*/

  }

  /*Deserialise la vue xml pour puvoir la placer dans l'activité*/
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_book_details, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) { /*si l'item selectionné correpond aux action on retourne true*/
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  /*Bouton pour revenir a l'activité principale*/
  public void onClickCloseButton(View v) {
    this.finish();
  }
}
