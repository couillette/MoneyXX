package com.moneyxx;

import com.entity.Project;
import com.entity.ProjectAccount;
import com.entity.UserAccount;
import com.server.PhoneData;
import com.server.SendEmailAsyncTask;
import com.server.StackmobQuery;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class JackpotProjectActivity extends Activity {
	
	String thisUserName;
	String thisUserEmail;
	String thisUserAccountID;
	String thisUserAccountSolde;
	
	String valueSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jackpot_project);
		
		// Enable the callBack button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		loadPrefs();
		
		valueSelected = ""+this.getIntent().getExtras().getInt("key");
		ImageView img = (ImageView) findViewById(R.id.imageView_JackpotProject_1);
		TextView tv1 = (TextView) findViewById(R.id.textView_JackpotProject_1);
		TextView tv2 = (TextView) findViewById(R.id.textView_JackpotProject_2);
		
		if (valueSelected.equals("1")) {
			img.setBackgroundResource(R.drawable.ic_aba);
			tv1.setText(project_11);
			tv2.setText(project_12);
		}
		if (valueSelected.equals("2")) {
			img.setBackgroundResource(R.drawable.ic_lilyhoney);
			tv1.setText(project_21);
			tv2.setText(project_22);
		}
		if (valueSelected.equals("3")) {
			img.setBackgroundResource(R.drawable.ic_birdchoregraphy);
			tv1.setText(project_31);
			tv2.setText(project_32);
		}
		if (valueSelected.equals("4")) {
			img.setBackgroundResource(R.drawable.ic_becrazy);
			tv1.setText(project_41);
			tv2.setText(project_42);
		}
	}
	
	public void onClick(View view) {
		if(view.getId()==R.id.button_JackpotProject_Donate){
			
			final String amount = ((EditText) findViewById(R.id.editText_JackpotProject_AmountOfMoney)).getText().toString();
			
			Builder alert_message = new AlertDialog.Builder(this);
			alert_message.setTitle("Warning");
			alert_message.setMessage("You are going to send " + amount
					+ " $ to : this project.\nAre you OK ?");

			alert_message.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
								if(creditDebit(thisUserName, valueSelected, amount)){

									// send message (mail or sms) to receiver
									StackmobQuery stQuery = new StackmobQuery();
									Project pro = stQuery.fetchProjectListByID(valueSelected).get(0);
									String mailProject = pro.getEmail();
									String[] mailAddressR = { mailProject };
									String subR = "New Donation";
									
									String messageR = "you just receive "+ amount+ "$ from "+ thisUserName + " and the project account was credited.";
									new SendEmailAsyncTask(mailAddressR,subR, messageR).execute();

									// send message (mail or sms) to sender
									String[] mailAddressS = { thisUserEmail.trim() };
									String subS = "Donation";
									String messageS = "you give "+ amount+ "$ to "+ pro.getprojectTitle() + " and your account was debited";
									new SendEmailAsyncTask(mailAddressS,subS, messageS).execute();
								}
						}
					});
			alert_message.setNegativeButton("NO",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int which) {
						}
					});
			alert_message.show();
		}
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jackpot_project, menu);
		return true;
	}
	
	
	
	private Boolean loadPrefs() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean AccountIsOK = pref.getBoolean("ACCOUNT", false);

		// Sign_up activity should be display only at the first time. For this
		// we need to store preference
		if (AccountIsOK) {;
			thisUserName = pref.getString("USERNAME", null);
			thisUserEmail = pref.getString("EMAIL", null);
			thisUserAccountID = pref.getString("ACCOUNTID", null);
			thisUserAccountSolde = pref.getString("SOLDE", null);

		} else {

			Builder build = new AlertDialog.Builder(this);
			build.setTitle("WARNING");
			build.setMessage("You have to fill your RIB and CreditCard in Wallet "
					+ "if you want to send and beg money !");
			
			build.setPositiveButton("Go to Wallet", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent myIntent = new Intent(JackpotProjectActivity.this,
							MyWalletActivity.class);
					JackpotProjectActivity.this.startActivity(myIntent);
					JackpotProjectActivity.this.finish();
				}
			});
			build.setNegativeButton("Cancel", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			build.show();
		}
		return AccountIsOK;
	}
	
	
	public Boolean creditDebit(String UserSenderNAME, String ProjectReceiverID, String amount) {
		boolean done = false;

		while (!done) {
			
			StackmobQuery stQuery3 = new StackmobQuery();
			int solde00 = stQuery3.fetchProjectAccountSolde(ProjectReceiverID);
			// int solde00 = 0;
			// credit "+"
			int solde11 = solde00 + Integer.parseInt(amount.trim());

			ProjectAccount pr = new ProjectAccount();
			pr.setID(ProjectReceiverID.trim());
			pr.setSolde("" + solde11);
			pr.save();

			done = true;
		}
		done = false;

		while (!done) {
			// debit the sender account
			int solde0 = Integer.parseInt(thisUserAccountSolde.trim());
			// debit "-"
			int solde1 = solde0 - Integer.parseInt(amount.trim());

			UserAccount us = new UserAccount();
			us.setID(thisUserAccountID);
			us.setSolde("" + solde1);
			us.save();

			PhoneData p = new PhoneData();
			p.savePrefs(this, "SOLDE", "" + solde1);

			done = true;
		}

		return done;
	}
	
	
	// Set the call back to return to previous activity
		@Override
		public boolean onOptionsItemSelected(MenuItem item){
			Intent myIntent ;
			switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.action_money_beg:
	            // action_money_beg
	        	myIntent = new Intent(JackpotProjectActivity.this, SendBegActivity.class);
	        	JackpotProjectActivity.this.startActivity(myIntent);
	        	this.finish();
	            return true;
	        case R.id.action_money_send:
	            // action_money_send
	        	myIntent = new Intent(JackpotProjectActivity.this, SendBegActivity.class);
	        	JackpotProjectActivity.this.startActivity(myIntent);
	        	this.finish();
	            return true;
	        case R.id.action_jackpot:
	            // action_jackpot
	        	myIntent = new Intent(JackpotProjectActivity.this, JackpotActivity.class);
	        	JackpotProjectActivity.this.startActivity(myIntent);
	        	this.finish();
	            return true;
	        case R.id.action_wallet:
	            // action_wallet
	        	myIntent = new Intent(JackpotProjectActivity.this, MyWalletActivity.class);
	        	JackpotProjectActivity.this.startActivity(myIntent);
	        	this.finish();
	            return true;
	        case R.id.action_settings:
	            // action_settings
	        	myIntent = new Intent(JackpotProjectActivity.this, SettingsActivity.class);
	        	JackpotProjectActivity.this.startActivity(myIntent);
	        	this.finish();
	            return true;      
			default:
				return super.onOptionsItemSelected(item);
			}
		}
	
	String project_11 = "Le schisme littéraire des témoignages de la Grande Guerre";
	String project_12 ="Je voudrais présenter dans cet article un résultat de ma recherche en thèse de doctorat1, " +
			"tel que je l’ai exposé en mars 2012 dans un colloque consacré à « Ce que le document fait à la littérature »\n" +
			"Il y a toujours un peu de vérité derrière les «je rigole», un peu de curiosité derrière les «je demandais " +
			"juste», un peu de savoir derrière les «je ne sais pas», un peu de mensonge derrière les «je m'en fous» et un " +
			"peu de souffrance derrière les «ça va merci»";
	
	String project_21 = "Tant va la cruche à l'eau qu'à la fin ...";
	String project_22 = "La clé pour vivre un peu plus fabuleusement est de savoir qui vous êtes et ce que vous aimez. " +
			"Je ne parle pas des choses énormes qui définissent notre vie-comme ce que vous voulez faire pour gagner sa vie," +
			" juste les petites choses. Quel est votre plat préféré à cuisiner pour des amis? Parfait. Quelle est votre " +
			"boisson de signature? Appelez. Quel est votre go-to uniforme pour cette seconde date? Rock it comme personne " +
			"d'autre.";
	
	String project_31 = "Les élites françaises ont honte de la France";
	String project_32 = "Non, il ne s'agit pas d'un vain vertigo, mais de mon amour pour ces mots, que je vous livre ainsi..." +
			"Mon âme s'abeausit à la redécouverte des mots tombés en défortune. Notre oreille moderne, aroutinée au " +
			"cailletage des tartouilleurs a oublié ces mots nicets de notre passé. Ces mots désestimés, qui, sans " +
			"turlupinade, révèlent la sapience de nos anciens qu'ils rappellent à notre remembrance.S'ils sont friolerie " +
			"pour le glossographe qui d'un ragoulement salue leur consonance, ils tombent aujourd'hui..";
	
	String project_41 = "A ciel ouvert : La folie à l'âge tendre";
	String project_42 = "Faites-vous une habitude de croire que la mort n'est rien pour nous, puisque le bien et le mal ne " +
			"peuvent avoir lieu que par le sentiment. Or, la mort est l'extinction de tout sentiment. Avec ce principe, on " +
			"sait user de cette vie mortelle: on ne s'avise point d'en attendre une autre pour jouir, et on renonce à ce " +
			"vain espoir de l'immortalité. Il ne peut même arriver rien qui nous rende malheureux, dès que nous sommes " +
			"parvenus à ne pas regarder la perte de la vie comme un malheur. ";

}
