import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.text.*;
public class application_optique{
    static appli_optique applic_optique;
    public static void main(String[] args) {
	applic_optique = new appli_optique("Fenetre de choix de programmes");
	applic_optique.run();
    }
}
class appli_optique extends Frame{
    static final double pi=4.*Math.atan(1.);
    static final double eps0=1/(36*pi*10*Math.pow(10000.0,2));
    static final int dim=31;
    boolean creation_d_un_ensemble_venue_d_un_ensemble=false,retour_taille_normale=false;
    int ensemble_d_ou_vient_l_ordre_de_creer=-1,iretour_taille_normale=0;
    final int 	top_demarre= 240;
    final int 	left_demarre= 50;
    final int 	bottom_demarre= 500;
    final int 	right_demarre= 800;
    static final String titre[]=new String[12];
    ensemble_optique ensemble[]=new ensemble_optique[2];
    int ppmouseh=0,ppmousev=0;Graphics gr;
    boolean relachee=false,pressee=false,cliquee=false,draguee=false;
    long temps_en_sec=0;int i_run;boolean creation_ensembles;
    long temps_initial_en_sec,temps_initial_en_secondes_prec=0,temps_minimum=1200;
    //commentaire comm=null;
    String d_ou_je_reviens;
    boolean comm_on=false,terminer_demo=false,demo_faite=false;
    private SimpleDateFormat formatter; 
    Color orange_pale;
    int n_ensembles;
    Image image_h;
    private MouseStatic mm;Thread Th1;
    boolean toutdebut,run_applet,dejapaint;
    Font times14=new Font("Times",Font.PLAIN,14);
    Font times_gras_14=new Font("Times",Font.BOLD,14);
    Font times_gras_24=new Font("Times",Font.BOLD,24);
    appli_optique(String s){ 
	super(s);
	toutdebut=true;	    dejapaint=false;
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    on_s_en_va();
		    dispose();
		    System.exit(0);
		};
	    });
	run_applet=true;
	System.out.println("init applet");
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	titre[0]="diffraction 1 fente";
	titre[1]="diffraction_et_interferences";
	titre[2]="Interferometre de Michelson.";
	titre[3]="Interferometre de Fabry Perot";
	titre[4]="Lame semi reflechissante en transmission";
	titre[5]="Lame semi reflechissante en reflexion";
	titre[6]="dioptre plan, dioptre spherique";
	titre[7]="deux dioptres spheriques: concavites identiques, ou opposees";
	titre[8]="lentille convergente, lentille divergente";
	titre[9]="L'oeil";
	titre[10]="Le microscope";
	titre[11]="Lunette de Galilee, jumelles";	setBackground(Color.white);
	formatter=new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy",Locale.getDefault());
	Date maintenant=new Date();orange_pale=new Color(140,90,0);
	temps_initial_en_sec=temps_en_secondes(maintenant);
	temps_initial_en_secondes_prec=temps_initial_en_sec;
	System.out.println("maintenant "+maintenant+" s "+temps_initial_en_sec);
	creation_ensembles=true;
	relachee=false;pressee=false;cliquee=false;draguee=false;
	pack();setVisible(true);	
	setSize(1000,700);
	setLocation(0,0);
	gr= getGraphics();
    }
    void on_s_en_va(){
	for(int iii=1;iii>=0;iii--){
	    if(ensemble[iii]!=null){
		if(ensemble[iii].plaque_photo!=null){
		    ensemble[iii].plaque_photo.dispose();
		}
		if(ensemble[iii].sp_couleur!=null){
		    ensemble[iii].sp_couleur.dispose();
		}
		ensemble[iii].dispose();
		ensemble[iii]=null;
	    }
	}
	n_ensembles=0;
    }
    public long temps_en_secondes(Date nun){
	formatter.applyPattern("s");
	int s=Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("m");
	int m=Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("h");
	int h=Integer.parseInt(formatter.format(nun));
	//System.out.println(" h "+h+" m "+m+" s "+s);
	return(h*3600+m*60+s);
    }    
    public void run(){
	int isleep=20;
	System.out.println(" Run ");
	relachee=false;pressee=false;cliquee=false;draguee=false;
    fin_de_programme:
	while(run_applet){
	    Date now=new Date();
	    temps_en_sec=temps_en_secondes(now);
	    //System.out.println("temps_en_sec "+temps_en_sec);
	    
	    if(temps_en_sec-temps_initial_en_sec>temps_minimum){
		run_applet=true;break fin_de_programme; 
	    }
	    //if((peindre&&(!creation_ensembless))||creation_ensembles)
	    if(toutdebut){
		peint(gr);
		    //while(!cliquee)
		    //System.out.println("toutdebut "+toutdebut+" cliquee "+cliquee);
		toutdebut=false;cliquee=false;
		d_ou_je_reviens="";
		System.out.println("&&&&&toutdebut "+toutdebut+" cliquee "+cliquee);
	    }
	    //
	    if(!dejapaint){
		System.out.println(" vers paint,run_applet "+run_applet);
		peint(gr);dejapaint=false;
		System.out.println(" &&&&vers paint ");
		peint(gr);
	    }
	    if(d_ou_je_reviens!=""){
		System.out.println("d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles);
		n_ensembles=0;
		setVisible(true);
		for(int i=0;i<100;i++)
		    System.out.println("****va peindre");
		dejapaint=false;
		peint(gr);
	    }
	    //System.out.println(" n_ensembles "+n_ensembles+" creation_ensembles"+creation_ensembles);
	    if(creation_ensembles)
		demarrer_application();
	    if(d_ou_je_reviens!=""){
		System.out.println("***d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles);
		d_ou_je_reviens="";
	    }
		
		//System.out.println(" apres creer_chambres");
	    if(n_ensembles!=0){
		for(int ii=n_ensembles-1;ii>=0;ii--){
		    appelle_ensemble(ii);
		    if(ensemble[ii].le_virer){
			toutdebut=ensemble[ii].command=="Revenir a la page initiale avec infos";
			if(toutdebut){
			    demo_faite=false;
			    terminer_demo=false;
			    dejapaint=false;
			    creation_ensembles=false;
			    eraserect(gr,0,0,1000,1600,Color.white);
			}
			creation_ensembles=true;
			//peindre=true;
			relachee=false;
			pressee=false;
			draguee=false;
			cliquee=false;
			d_ou_je_reviens=ensemble[ii].command;
			System.out.println("*ii "+ii+" n_ensembles "+n_ensembles+" command "+ensemble[ii].command);
			n_ensembles=0;
			
			eliminer(1-ii);
			eliminer(ii);
			
		    }
		    if(ensemble[ii]==null)
			break;
		}
		if(creation_d_un_ensemble_venue_d_un_ensemble){
		    creation_d_un_ensemble_venue_d_un_ensemble=false;
		    if(ensemble_d_ou_vient_l_ordre_de_creer!=0)
			if(ensemble[ensemble_d_ou_vient_l_ordre_de_creer]!=null)
			    ensemble[ensemble_d_ou_vient_l_ordre_de_creer].re_dessin();
		}
	    }
	    //	    if(comm_on&&(i_run==5)&&(n_ensembles!=0))comm.ecrit_aide();
	    i_run++;if(i_run==20)i_run=0;
	    try {Thread.sleep(isleep);}
	    catch(InterruptedException signal){System.out.println("catch ");}
	}
	System.out.println(" run_applet "+run_applet);
	peint(gr);
	for(int ii=0;ii<n_ensembles;ii++){
	    ensemble[ii].dispose();
	    ensemble[ii]=null;
	}
	dispose();
	System.exit(0);
    }
    void eliminer(int ien){
	if(ensemble[ien]!=null){
	    System.out.println("RRRRRRRRRRRRevenir");
	    if(ensemble[ien].command=="Sortir du programme")
		run_applet=false;	    
	    //	    if(n_ensembles==2)cal=ensemble[1-i_ens].equipoting&&!ensemble[1-i_ens].montrer_cosmiques;
	    //if(!calcul_equip&&!montrer_cosmiques&&!cal){
	    //setVisible(false);
	    creation_ensembles=true;
	    dejapaint=false;
	    setVisible(true);
	    d_ou_je_reviens="je reviens de i_ens "+ien;
	    //command="";
	    ensemble[ien].command="";
	    //setVisible(false);
	    if(ensemble[ien].interferon!=null)
		if(ensemble[ien].sp_couleur!=null){
		    ensemble[ien].sp_couleur.dispose();
		    ensemble[ien].sp_couleur=null;
		}
	    if(ensemble[ien].plaque_photo!=null){
		ensemble[ien].plaque_photo.dispose();
		ensemble[ien].plaque_photo=null;
	    }
	    n_ensembles=0;
	    ensemble[ien].dispose();
	    ensemble[ien]=null;
	}
    }

    void appelle_ensemble(int iii){
	if(ensemble[iii]!=null){
	    //System.out.println("ensemble[iii].command "+ensemble[iii].command+" ensemble[iii].interferon.vibration "+ensemble[iii].interferon.vibration);
	    if(ensemble[iii].fab_lame_mich!=null)
		if(ensemble[iii].vibration)
		    ensemble[iii].interferon.interferometre_vibre();
	    ensemble[iii].dessin();
	}
    }
    void demarrer_application(){ 
	//System.out.println("dem relachee "+relachee+" pressee "+pressee);
	if(cliquee){
	    //	if(relachee&&pressee){
	    System.out.println("11");
	    int xi=ppmouseh;int yi=ppmousev;
	    cliquee=false;
	    if((xi > left_demarre)&&(xi < right_demarre)){
		for(int i=0;i<12;i++){
		    if((yi > top_demarre-4+i*24)&&(yi < top_demarre-4+(i+1)*24)){
			if(i<6||i==9||i==10){
			    n_ensembles=1;
			    creation_d_un_ou_deux_ensembles(0,i);
			}else{
			    n_ensembles=2;
			    for(int iii=0;iii<2;iii++)
				creation_d_un_ou_deux_ensembles(iii,i*10+iii);
			}
			for(int iii=0;iii<n_ensembles;iii++){
			    ensemble[iii].du_nouveau_a_voir=true;
			    ensemble[iii].dessin();
			}
			eraserect(gr,0,0,1000,1600,Color.white);
			gr.setColor(Color.black);
			gr.drawImage(image_h,50,400,this);
			relachee=false;pressee=false;
			creation_ensembles=false;
		    }
		}
	    }
	}		
    }
    public void creation_d_un_ou_deux_ensembles(int iii,int i_demarre){
	String str="Fenetre de demonstration";
	if(i_demarre>=0)
	    if(i_demarre<6||i_demarre==9||i_demarre==10)
		str=titre[i_demarre];
	    else
		if(i_demarre==60)
		    str="dioptre plan";
		if(i_demarre==61)
		    str="dioptre spherique";
		else if(i_demarre==70)
		    str="deux dioptres spheriques de concavites identiques";
		else if(i_demarre==71)
		    str="deux dioptres spheriques de concavites opposees";
		else if(i_demarre==80)
		    str="lentille convergente";
		else if(i_demarre==81)
		    str="lentille divergente";
		else if(i_demarre==110)
		    str="Lunette de Galilee";
		else if(i_demarre==111)
		    str="jumelles";

	ensemble[iii]=new ensemble_optique(str,iii,i_demarre,this);
	ensemble[iii].comment_init="";
	//setVisible(false);
	//ensemble[iii].setVisible(false);ensemble[iii].setVisible(true);
	System.out.println(" ensemble "+iii);
    }
    void appelle_doc_premiere_page(Graphics g){
	System.out.println(" eeeeeeeeeeeeeeeeeeeeeeeeeeeentree dans appelle_doc_premiere_page ");
	String name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_premiere_page.jpg";
	Image image=createImage(400,800);
	Graphics gTTampon=image.getGraphics();
	image=Toolkit.getDefaultToolkit().getImage(name);
	MediaTracker tracker=new MediaTracker(this);
	tracker.addImage(image,1);
	System.out.println(" tracker "+tracker); 
	try {tracker.waitForAll();
	}
	catch (InterruptedException e){
	    System.out.println(" image pas arrivee?");
	}
	System.out.println(" image "+image);
	g.drawImage(image,450,0,this);
	gTTampon.dispose();
	image.flush();
	System.out.println(" eeeeeeeeeeeeeeeeeeeeeeeeeesortie dans appelle_doc_premiere_page ");
    }
    public  void peint(Graphics g){
	int ix,iy;
	    System.out.println("deb dans paint,toutdebut"+toutdebut+" dejapaint "+dejapaint+" run_applet) "+run_applet);
	    if((!toutdebut)&&dejapaint&&run_applet)return;
	    if(toutdebut){
		appelle_doc_premiere_page(g);
		String name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_conseils.jpg";
		image_h=createImage(400,400);
		Graphics gTTmpn=image_h.getGraphics();
		image_h=Toolkit.getDefaultToolkit().getImage(name);
		MediaTracker tracker2=new MediaTracker(this);
		tracker2.addImage(image_h,1); 
		try {tracker2.waitForAll(); }
		catch (InterruptedException e){
		    System.out.println(" image_h pas arrivee?");
		}
		System.out.println(" demo_faite "+demo_faite+" cliquee "+cliquee);
		terminer_demo=false;
		if(!demo_faite){
		    if(!cliquee){
			creation_d_un_ou_deux_ensembles(0,-1);
			n_ensembles++;
		    }else{
			cliquee=false;
			terminer_demo=true;
		    }
		    if(!cliquee){
			creation_d_un_ou_deux_ensembles(1,-1);
			n_ensembles++;
		    }else{
			cliquee=false;
			terminer_demo=true;
		    }
		    while(!terminer_demo)
			if(!cliquee){
			    if(retour_taille_normale){
				iretour_taille_normale++;
				retour_taille_normale=false;
				if(iretour_taille_normale<=5)
				    appelle_doc_premiere_page(g);
				else
				    iretour_taille_normale=0;
			    }
			    for(int iii=1;iii>=0;iii--){
				//ensemble[iii].du_nouveau_a_voir=true;
				appelle_ensemble(iii);
			    }
			}else{
			    terminer_demo=true;
			    fermer_ensembles_demo(g);
			    System.out.println(" terminer_demo ");
			}
		    demo_faite=true;
		}
		System.out.println(" cliquee$$$$$ "+cliquee);
		if(terminer_demo){
		    System.out.println(" on a detruit les ensembles de demo ");
		    cliquee=false;relachee=false;pressee=false;draguee=false;
		    eraserect(g,0,0,1000,1600,Color.white);
		}
		//if(fois==1)
		//peindre=false;
		return;
	    }else{
		System.out.println("passage dans paint,dejapaint "+dejapaint);
		if(run_applet){
		    if(!dejapaint){
			dejapaint=true;
			eraserect(g,0,0,1000,1600,Color.white);
			int j=0;
			for(int i=0;i<200000;i++)
			    j++;		    
			//if(creation_ensembles){
			int ppv;
			g.setFont(times_gras_24);
			g.setColor(Color.green);
			g.drawString("Cliquez dans ce menu pour un ensemble de votre choix.",left_demarre,top_demarre-120);	      
			g.setColor(Color.red);
			g.drawString("En rouge, dispositifs interferometriques.",left_demarre,top_demarre-90);	      
			g.setColor(Color.blue);
			g.drawString("En bleu, optique geometrique.",left_demarre,top_demarre-60);	      
			paintrect_couleur(g,top_demarre-4,left_demarre,bottom_demarre+24,right_demarre,Color.red);
			g.setFont(times_gras_24);
			for(int i=0;i<12;i++){
			    if(i>=6)
				g.setColor(Color.blue);
			    g.drawString(titre[i],left_demarre+10,top_demarre+17+i*24);//0
			    drawline_couleur(g,left_demarre,top_demarre+20+i*24,right_demarre,top_demarre+20+i*24,Color.red);
			}
		    }
		}else{
		    System.out.println("erase g ");
		    eraserect( g,0,0,1000,1600,Color.white);
		    g.setFont(times_gras_24);g.setColor(Color.black);
		    if(temps_en_sec-temps_initial_en_sec>temps_minimum){
			for(int i=0;i<20;i++)
			    g.drawString("TEMPS MAXIMUM EXPIRE",100,100);
		    }
		    for(int i=0;i<20;i++){
			g.drawString("FIN DU PROGRAMME",100,150);
			g.drawString("POUR REVENIR A INTERNET,SUPPRIMEZ CETTE FENETRE.",100,200);
		    }
		}
	    }
	    System.out.println("fin paint(g)");
	    
    }
    void fermer_ensembles_demo(Graphics gg){
	eraserect(gg,0,0,1000,1600,Color.white);
	ensemble[0].dispose();
	ensemble[0]=null;
	ensemble[1].dispose();
	ensemble[1]=null;
	//setVisible(true);
	d_ou_je_reviens="je reviens de num_fen";
	n_ensembles=0;
	demo_faite=true;
    }
    void drawline_couleur(Graphics g,int xin,int yin,int xfin,int yfin,Color couleur){
	g.setColor(couleur);g.drawLine(xin,yin,xfin,yfin);
    }
    void drawline_couleur_print(Graphics g,int xin,int yin,int xfin,int yfin,Color couleur){
	System.out.println("xin "+xin+" yin "+yin+" xfin "+xfin+" yfin "+yfin);
	g.setColor(couleur);g.drawLine(xin,yin,xfin,yfin);
    }
    void paintrect_couleur(Graphics g,int top,int left,int bot,int right,Color couleur){
	int x,y,width,height;
	x=left;y=top;width=right-left;height=bot-top;
	g.setColor(couleur);g.drawRect(x,y,width,height);
    }    
    void remplisrect(Graphics g,int top,int left,int bot,int right,Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.fillRect(x,y,width,height);
    }    
    
    void paintrect(Graphics g,int top,int left,int bot,int right)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(Color.black);g.drawRect(x,y,width,height);
    }
    void paintcircle(Graphics g,int xx,int yy,int rr){
	int x,y,r;
	x=xx;y=yy;r=rr;
	g.setColor(Color.blue);g.fillOval(x,y,r,r);
    }
    void eraserect(Graphics g,int top,int left,int bot,int right,Color couleur){
	int x,y,width,height;
	x=left;y=top;width=right-left;height=bot-top;
	g.setColor(couleur);g.fillRect(x,y,width,height);
	System.out.println(" left "+left+" top "+top+" bot "+bot+" right "+right+" g "+g);
    }
    void invertrect(Graphics g,int top,int left,int bot,int right)
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    //g.setColor(Color.black);g.fillRect(x,y,width,height);
    }
    void paintcircle_couleur(Graphics g,long x,long y,long r,Color couleur){
	g.setColor(couleur);g.fillOval((int)(x-r/2),(int)(y-r/2),(int)r,(int)r);
    }

    public void	traite_click(){
	System.out.println("entree traite_click "+"cliquee "+cliquee+"pressee "+pressee+"relachee "+relachee);
	if(cliquee){
	    Date maintenant=new Date();
	    temps_initial_en_sec=temps_en_secondes(maintenant);
	    if(temps_initial_en_sec<temps_initial_en_secondes_prec+2){
		cliquee=false;pressee=false;relachee=false;
	    }else
		temps_initial_en_secondes_prec=temps_initial_en_sec;
	}
	if(cliquee&&!toutdebut&&n_ensembles!=0){
	    cliquee=false;pressee=false;relachee=false;
	    for(int ik=0;ik<2 ;ik++){    
		ensemble[ik].le_virer=true;
		ensemble[ik].command="Revenir a la page principale";
	    }
	}
    }
    class MouseStatic extends MouseAdapter{
	appli_optique subject;
	public MouseStatic(appli_optique a){
	    subject=a;
	}
	public void mouseClicked(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //for( int iq=0;iq<ens_de_cyl[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=true;relachee=true;
	    System.out.println("relachee "+relachee);
	}
    }
}

class ensemble_optique extends Frame implements ActionListener{
    static final double pi=4.*Math.atan(1.),c=3e10;
    double a_trinome=0.,bp_trinome=0.,c_trinome=0.,delta_trinome=0.;
    int top_ens_cyl=0,left_ens_cyl=0,bottom_ens_cyl=350,right_ens_cyl=400;
    static final double sqrt2=Math.sqrt(2);boolean le_virer=false;
    boolean faisceau_incident_parallele=false;
    int ppmouseh=0,ppmousev=0,ppmouseh_prec=0,ppmousev_prec=0,i_ens=0;int num_obj_dep=-1,num_dep=0,identite_menu=0;
    microscope mikroscop;lunette_galilee galilee_brille;jumelles fernseh; 
    double angle_limite_initial0=0.125,angle_limite_initial=0.125;
    int toto_arret=0;	boolean vibration=false;
    spectre_couleur sp_couleur;
    boolean michelson_deregle=false,faces_perot_lame_non_paralleles=false;
    boolean seulement_pour_montrer_rayons=false,dessine_la_plaque=false,intensites_de_base_deja_calculees=false,source_etendue=false,explique_lumiere_composee=false,expliquer_vibrations=false;
    final double distance_avant_arriere[]=new double[10];
    double chm_opti[][]=new double[300][5];
    int dppp_y=0,dppp_z=0;	triple_double toto_trd,tata_trd;
     final MenuItem itep_size_plus=new MenuItem("Multiplier l'echelle du dispositif par 2");
    final MenuItem itep_size_moins=new MenuItem("Diviser l'echelle du dispositif par 2");
    final MenuItem rapprocher_de[]=new MenuItem [10];
    final MenuItem facteur_refl[]=new MenuItem [10];
    String toto_string="";
    final String rapprocher_de_[]=new String[10];
    final MenuItem reculer_de[]=new MenuItem [10];
    final String reculer_de_[]=new String[10];
    final String facteur_refl_[]=new String [10];
    final double fact_ref[]=new double [10];
    final String vibration_de_[]=new String[10];
    int dpt_y_source_etendue=0,dpt_z_source_etendue=0;
    double int_max_pour_plot=0,cms_par_pixels=0.2,cms_par_pixels0=0.2;
    double fact_cms_par_pixels=1.,facteur_d_amplitude=0.;
    long temps_action=0,temps_action_prec=0;
    triple_int trp_int;
    boolean trouve_deplacement=false,deplacer_un_objet=false,fin_deplacer_un_objet=false,points_rouges_allumes=false,dessine_parcours=false;
    Image image_appareil;
    Graphics gTampon_appareil;
    MediaTracker tracker_appareil;boolean grande_taille=false;
    MediaTracker trackerr=new MediaTracker(this);
    Image image_intensites;
    Graphics gTampon_intensites;
    boolean points_a_deplacer_deja_definis=false,ajout_lentille_oeil=false;
    interferometre_michelson interf_mich;diffraction_interferences diffr_int;
    fabry_perot fbr_prt;lame_par_transmission lame_int;lame_par_reflexion lame_par_reflex;
    boolean arreter=false;
    MenuBar mb1[]=new MenuBar[2];private MouseMotion motion;private MouseStatic mm;
    String titre_fenetre="";
    MenuItem itep_mettre_lentille_finale=new MenuItem("mettre une lentille sur le trajet final.");
    double aaa=0.,bbb=0.,y_sur_lobjet=0.,y_sur_le_plan_focal=0.,coef_ang_fin=0.;
    double pt1=0.;
    double x_intersec=0.,y_intersec=0.;
    point p_intersec,pt_end;
    MenuItem itab[]=new MenuItem [11];
    Menu menu_vib, itep_coin,itep_miroir_gauche_deregle,itep_miroir_bas_deregle;
    Menu profondeur;
    Menu rapprocher,reculer;
    int n_vibrations=0;
    double ampl_vib=0.,xiimage=0.,yiimage=0.;
    final MenuItem vibration_de[]=new MenuItem [10];
    point_coeff pc_util,pc_util1,pc_util2,pc_util3,pc_util4,pc_util5,pc_util6,pc_garde,toto_pt_coeff,pc_exit;
    double diff_chem=0.;
    point_coeff pc_trouve_a,pc_trouve_b;
    double ordonnee_origine_a=0.,ordonnee_origine_b=0.;
    double angle_vib=0.001,d_angle_vib=pi/16.;
    double angle=0.,sin_deflechi=0.,angle_deflechi_avec_ox=0.,coeff_avant=0.,epaisseur=0.,coco=0.,cece=0.,caca=0.,cici=0.,cucu=0.,didi=0.,dada=0.,dodo=0.,d_dodo=0.,dodo_prec=0.,aoao=0.,d_yyy=0.,dy_trouve=0.,dy_trouve_prec=0.,d_pt_prec=0.,d_pt=0.,d_d_pt=0.;
    int toto_int=0,tata_int=0,titi_int=0,tutu_int=0;long toto_long=0;
    int toto_int_y=0,toto_int_z=0;
    double x_sur_lobjet=0.,x_sur_le_plan_focal=0.,distance_au_centre_focal=0.,distance_au_centre=0.,distance_au_centre_niveau_lentille=0.;
    point pt_final,zer;
    plaque_photographique plaque_photo,plaque_photo_prime;
    boolean lumiere_composee=false;
    Graphics grp_c;
    int nb_de_couleurs=5;
    triple_double colrvb[][]=new triple_double[301][300];
    triple_double trifacteur,tri_db,comrvb;
    int RR=0,VV=0,BB=0;
    double intensite_relative=0.,intensite_totale=0.;
    point pt_souris,dpt_souris;
    boolean relachee,pressee,cliquee,draguee;int bandeau_x,bandeau_y;
    objectt objet[]=new objectt[20];int i_demarre;
    interferometres interferon;fabry_perot_ou_lame_ou_mich fab_lame_mich;fabry_perot_ou_lame fab_lame;
    source src;lame_semireflechissante lame_semi;lame_a_faces_paralleles lame;
    String command_prec="",command="",comment="Utilisez les menus",comment_init="";
    lentille lentle_1,lentle_2,lentle_3;PM photom;
    miroir_plan miroir_gauche,miroir_bas;
    boolean fin_gerelesmenus_avec_souris=false,rets=false,stoppers=false,occupied=false;
    int icoul=0;
    double ypoint[]=new double [1001];
    double sisin=0.,cocos=0.,sisisin=0.,cococos=0.;
    int xx=0,yy=0,ptt=0,theta=0,idx=0,idy=0,xcc=0,ycc=0,nener=0,iessai=0,origine=0,num_obj_lentille1=-1,num_obj_laser=-1,num_obj_perot=-1,num_obj_perot_lame=-1,num_obj_lame_semi=-1,num_obj_fentes=-1,num_obj_pm=-1,num_obj_miroir_gauche=-1,num_obj_miroir_bas=-1,num_obj_lentille2=-1,num_obj_lentille3=-1,num_obj_source=-1,num_obj_lame=-1;
    double dx=0.,dy=0.,d2=0.,norm=0.,ypr=0.;
    int  xmin=0,xmax=0,ymin=0,ymax=0,xi=0,yi=0,mesure=0;
    int indice_lambda[]=new int[10];int indice_lamb=50;
    int nb_commande=0;
    double energie=0.,xep=0.,yep=0.,y_du_PM=0.,coef_ang_1=0.,coef_ang_2=0.,a=0.,R=0.;
    int yj=0,nb_lentilles_apres_fente=0,nb_lames_apres_fente=0,nb_lentilles_avant_fente=0,nb_lames_avant_fente=0;
    double intensite=0.,dist_foc=0.,ypo=0.,lambd=0.;
    double intensite_norme=0.,intensite_max=0.;int pointt_max=0;
    double int_norm_monochr=0.,int_norm_lum_bl=0,int_norm_etendue_monochr=0,int_norm_etendue_lum_bl=0;
    double eps=0.,y_centre=0.,x_slit=0.,y_slit=0.,grandissement=0.;
    int la_source=0,la_lentille=2,miroir_plan=3,le_dioptre_plan=4,le_dioptre_spherique=5,les_fentes=6,lame_semireflechissante=7,lame_a_faces_paralleles=8,le_perotfabry=9,le_PM=10;
    MenuItem itep_couleur=new MenuItem("Au centre, intensite fonction de la longueur d'onde");
    MenuItem itep_agrandir=new MenuItem("Agrandir cette fenetre d'un facteur 2");
    Menu itep_composee=new Menu("Source de lumiere composee");
    MenuItem item_spectre_de_raies_2=new MenuItem("2 raies, une bleue, une rouge");
    MenuItem item_spectre_de_raies_3=new MenuItem("2 raies, une verte, une rouge");
    MenuItem item_spectre_de_raies_1=new MenuItem("spectre de raies, dominante bleue");
    MenuItem item_spectre_continu=new MenuItem("spectre continu");
    int ipt_max=150;
    point pointf_image,ppp_s,ppp_slit,toto,titi,tata,tutu,tete;
    boolean toto_boolean=false,toto_bool_autre_am=false;
    double  coef_ang_inita=0.,coef_ang_finala=0.,costheta=0.;
    double coef_ang_init=0.,distance_apres=0.,distance0_s=0.,distance_avant=0.;
    String stre;boolean du_nouveau_a_voir=false;
    boolean autrechoix=false,trouve=false;
    int nb_objets=0,iq=0,jq=0;
    double logfreq,anq,xfoyer,yfoyer;
    boolean conseil=false;
    boolean modify,diffraction, releve_courbe_PM;
    boolean perot_lame=false,michelsonne=false,perot_fabryyyy=false,lame_reflexion=false,lame_transmission=false;
    double coeff_initial=0.;
    double difference_phase_michelson=0.,chemin_optique_prec=0.;
    double x_double_s,y_double_s,dephasage,lambda,lambdaq=0.,lambda0,lambda_min=0.,lambda_max=0.;
    couleur couleur_de_la_lumiere,couleur_utilisee,coucoul,toto_coul;Color couleur_lumiere;double couleur_max_max=0.,max_de_la_couleur=0.;
    couleur couleur_composee[]=new couleur[300];couleur color_max,couleur_rvb;
    static final couleur farbe_bei_laenge[]=new couleur [100]; 
    String nom_fichier;appli_optique subject;
    
    double echelle_transverse=0.,echelle_transverse0=0.,echelle_source_etendue=10.;
    static double min2=0.4e-4,max2=0.7e-4;
    //la longueur unite est le cm (voir la longueur d'onde lambda,dans le visible),et un pixel=1/100 cm (0.1mm) pour echelle_transverse =100. L'echelle longitudinale est unite: 1 pixel=1cm. Pour le peror,l'echelle transverse est de 1000,1 pixe=0.01 mm,=10µ.
    static final int longueur_laser=40,longueur_support_laser=100,ycarre=20,xlaser=80,xlentille=120,xmiroir_plan=160,xmiroir_sph=200,xslits=240,xlamesemireflechissante=280,xperot=320,xPM=360;
    final static int top2=75,left2=10,right2=110,bot2=103;
    double y_banc=200.;
    boolean interferometrie=false;
    static final int diffraction_1_fente=0,diffraction_et_interferences=1,michelson_lame_d_air=2,perot_fabry=3,lame_en_transmission=4,lame_en_reflexion=5,dioptre_plan=60,dioptre_spherique=61,concavites_identiques=70,concavites_opposees=71,lentille_convergente=80,lentille_divergente=81,oeil=9,microscope=10,lunette_galilee=110,jumelles=111,depl=19,deph=20,actions=5,releve_courbe=2;
    optique_geometrique optic_geo;
    static final int toptext=25,lefttext=0,bottomtext=80,righttext=600,topdraw=80,leftdraw=0,bottomdraw=600,rightdraw=600;
    
    ensemble_optique(String s,int i_ens1,int i_demarre1,appli_optique sub1){
	super(s);
	titre_fenetre=s;
	subject=sub1;
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    /*
		    while(occupied){
			System.out.println("occupe"); 	
		    }
		    */
		    le_virer=true;
		};
	    });
	i_demarre=i_demarre1;
	if(i_demarre>=6)
	    right_ens_cyl=800;
	i_ens=i_ens1;command="";
	bandeau_x=20;
	if(i_demarre<=0||i_demarre==2)
	    bandeau_y=bottom_ens_cyl-40;
	else if(i_demarre==2){
	    bandeau_x=200;
	    bandeau_y=bottom_ens_cyl-40;;
	}else
	    bandeau_y=bottom_ens_cyl-40;
	mb1[i_ens]=null;
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	motion=new MouseMotion(this);
	this.addMouseMotionListener(motion);
	pack();
	comrvb=new triple_double(0.,0.,0.);
	trifacteur=new triple_double(0.,0.,0.);
	toto_trd=new triple_double(0.,0.,0.);
	tata_trd=new triple_double(0.,0.,0.);
	tri_db=new triple_double(0.,0.,0.);
	couleur_rvb=new couleur(0,0,0);
	trp_int=new triple_int(0,0,0);
	itep_composee.add(item_spectre_de_raies_2);  
	itep_composee.add(item_spectre_de_raies_3);  
	itep_composee.add(item_spectre_de_raies_1);  
	itep_composee.add(item_spectre_continu); 
	menu_vib=new Menu("Vibration stoppee par pression sur souris");
        itep_coin=new Menu("coin");
	itep_miroir_gauche_deregle=new Menu("Miroir gauche deregle");
	itep_miroir_bas_deregle=new Menu("Miroir bas deregle");
	vibration_de_[0]="amplitude 0.01µ";
	vibration_de_[1]="amplitude 0.02µ";
	vibration_de_[2]="amplitude 0.05µ";
	vibration_de_[3]="amplitude 0.1µ";
	vibration_de_[4]="amplitude 0.2µ";
	vibration_de_[5]="amplitude 0.5µ";
	vibration_de_[6]="amplitude 1µ";
	vibration_de_[7]="amplitude 2µ";
	vibration_de_[8]="amplitude 5µ";
	vibration_de_[9]="amplitude 10µ";
	if(i_demarre!=microscope&&i_demarre!=lame_en_transmission&&i_demarre!=lame_en_reflexion){
	    distance_avant_arriere[0]=0.01e-4;
	    distance_avant_arriere[1]=0.02e-4;
	    distance_avant_arriere[2]=0.05e-4;
	    distance_avant_arriere[3]=0.1e-4;
	    distance_avant_arriere[4]=0.2e-4;
	    distance_avant_arriere[5]=0.5e-4;
	    distance_avant_arriere[6]=1.e-4;
	    distance_avant_arriere[7]=2.e-4;
	    distance_avant_arriere[8]=5.e-4;
	    distance_avant_arriere[9]=10.e-4;
	}else{
	    distance_avant_arriere[0]=0.1;
	    distance_avant_arriere[1]=0.2;
	    distance_avant_arriere[2]=0.4;
	    distance_avant_arriere[3]=0.7;
	    distance_avant_arriere[4]=1.;
	    distance_avant_arriere[5]=1.5;
	    distance_avant_arriere[6]=2;
	    distance_avant_arriere[7]=3.;
	    distance_avant_arriere[8]=5;
	    distance_avant_arriere[9]=10;
	}
	for(int ik=0;ik<10;ik++)
	    vibration_de[ik]=new MenuItem(vibration_de_[ik]);
	zer=new point(0.,0.);
	dpt_souris=new point(zer);
	pt_souris=new point(zer);
	pt_final=new point(zer);
	pc_util=new point_coeff(0.,0.,0.);
	pc_util1=new point_coeff(0.,0.,0.);
	pc_util2=new point_coeff(0.,0.,0.);
	pc_util3=new point_coeff(0.,0.,0.);
	pc_util4=new point_coeff(0.,0.,0.);
	pc_util5=new point_coeff(0.,0.,0.);
	pc_util6=new point_coeff(0.,0.,0.);
	pc_exit=new point_coeff(0.,0.,0.);
	toto_pt_coeff=new point_coeff(0.,0.,0.);
	pc_garde=new point_coeff(0.,0.,0.);
	pc_trouve_a=new point_coeff(0.,0.,0.);
	pc_trouve_b=new point_coeff(0.,0.,0.);
	couleur_utilisee= new couleur(0,0,0);
	color_max=new couleur(255,255,255);
	couleur_rvb=new couleur(255,255,255);
	int v=0,w=0;
	for(int i=0;i<100;i++){
	    if(i<27)  //ultra bleu
		farbe_bei_laenge[i]= new couleur(0,0,(int)(255*i/27.));
	    else if(i<50){ //entre bleu et vert
		v=i-27;
		w=50-i;
		if(v>w){
		    w=(int)(255.*w)/v;
		    v=255;
		}else{
		    v=(int)(255.*v)/w;
		    w=255;		    
		}
		farbe_bei_laenge[i]= new couleur(0,v,w);
	    }
	    else if(i<83){ //entre vert et rouge
		v=i-50;
		w=83-i;
		if(v>w){
		    w=(int)(255.*w)/v;
		    v=255;
		}else{
		    v=(int)(255.*v)/w;
		    w=255;		    
		}
		farbe_bei_laenge[i]= new couleur(v,w,0);		
		//farbe_bei_laenge[i]= new couleur((int)(255*(i-50)/33.),(int)(255*(83-i)/33.),0);		
	    }else if(i>=83)//infra rouge
		farbe_bei_laenge[i]= new couleur((int)(255*(100-i)/17.),0,0);
	}			
	p_intersec=new point(zer);
	pt_end=new point(zer);
	ppp_s=new point(zer);
	ppp_slit=new point(zer);
	pointf_image=new point(zer);
	toto=new point(zer);titi=new point(zer);tata=new point(zer);tutu=new point(zer);tete=new point(zer);
	if(i_demarre==9||i_demarre==10)
	    bottom_ens_cyl*=2;
	setSize(right_ens_cyl-left_ens_cyl,bottom_ens_cyl-top_ens_cyl);
	setLocation(left_ens_cyl,top_ens_cyl+i_ens*350);
	setVisible(true);
	grp_c=getGraphics();
	perot_lame=false;
	modify=false;
	diffraction=false;
	yj=1;
	conseil=true;
	double y_lentille=0.;
        ptt=0;
	if(i_demarre==-1)
	    if(i_ens==0)
		diffraction=true;
	    else if(i_ens==1)
		michelsonne=true;
	if(i_demarre==diffraction_1_fente||i_demarre==diffraction_et_interferences||i_demarre==-1&&i_ens==0){
	    diffr_int=new diffraction_interferences();
	    interferon=diffr_int;
	    System.out.println("^^^^^^^^^^diffr_int "+diffr_int+" interferon "+interferon);
	}
	if(i_demarre==perot_fabry){
	    fbr_prt=new fabry_perot();
	    interferon=fbr_prt;
	    fab_lame_mich=fbr_prt;
	    fab_lame=fbr_prt;
	}
	if(i_demarre==lame_en_transmission){
	    lame_int=new lame_par_transmission();
	    interferon=lame_int;
	    fab_lame_mich=lame_int;
	    fab_lame=lame_int;
	}
	if(i_demarre==lame_en_reflexion){
	    lame_par_reflex=new lame_par_reflexion();
	    interferon=lame_par_reflex;
	    fab_lame_mich=lame_par_reflex;
	    fab_lame=lame_par_reflex;
	}
	if(i_demarre==michelson_lame_d_air||i_demarre==-1&&i_ens==1){
	    interf_mich=new interferometre_michelson();
	    interferon=interf_mich;
	    fab_lame_mich=interf_mich;
	}
	if(i_demarre>=60||i_demarre==9||i_demarre==10)
	    ouvre_classe();
	indice_lamb=50;
	lambda0=min2+50*(max2-min2)/100.;
	lambda=lambda0;
	if(interferometrie){
	    int i=(int)((lambda-min2)/(max2-min2)*100.);
	    couleur_de_la_lumiere=new couleur(farbe_bei_laenge[i]);
	    coucoul=new couleur(0,0,0);
	    couleur_de_la_lumiere.print("i "+i+" couleur_de_la_lumiere ");
	    couleur_lumiere=couleur_de_la_lumiere.col;
	    couleur_de_la_lumiere.print(" depart couleur_de_la_lumiere ");
	}else
	    couleur_lumiere=Color.black;
	toto_coul=new couleur(0,0,0);
	//farbe_bei_laenge[104]=null;facteur_refl_
	if(interferometrie&&perot_lame&&!lame_transmission)
	    for(int ik=0;ik<10;ik++){
		fact_ref[ik]=Math.round(ik*0.1*100)/100.;
		if(ik==9)
		    fact_ref[ik]=0.95;
		facteur_refl_[ik]=" "+fact_ref[ik]+".";
		facteur_refl[ik]=new MenuItem(facteur_refl_[ik]);
	    }
	if(i_demarre!=microscope&&!lame_reflexion&&!lame_transmission){
	    rapprocher_de_[0]=" 0.01µ";
	    rapprocher_de_[1]=" 0.02µ";
	    rapprocher_de_[2]=" 0.05µ";
	    rapprocher_de_[3]=" 0.1µ";
	    rapprocher_de_[4]=" 0.2µ";
	    rapprocher_de_[5]=" 0.5µ";
	    rapprocher_de_[6]=" 1µ";
	    rapprocher_de_[7]=" 2µ";
	    rapprocher_de_[8]=" 5µ";
	    rapprocher_de_[9]=" 10µ";
	    for(int ik=0;ik<10;ik++)
		reculer_de_[ik]=" "+rapprocher_de_[ik];
	}else{
	    for(int ik=0;ik<10;ik++){
		rapprocher_de_[ik]=" "+distance_avant_arriere[ik]+" pixels";
		reculer_de_[ik]=" "+rapprocher_de_[ik];
	    }
	}
	for(int ik=0;ik<10;ik++){
	    if(!(ik>5&&lame_reflexion)){
		rapprocher_de[ik]=new MenuItem(rapprocher_de_[ik]);
		reculer_de[ik]=new MenuItem(reculer_de_[ik]);
	    }
	}
	barre_des_menus();
	if(plaque_photo==null&&interferometrie&&i_demarre!=-1)
	     plaque_photo=new plaque_photographique("Vue de l'ecran",i_ens);
	for(int ptt_y=0;ptt_y<300;ptt_y++)
	    couleur_composee[ptt_y]= new couleur(0,0,0);
	toto_int=bottom_ens_cyl-top_ens_cyl;
	//if(interferometrie)
	    image_appareil=createImage(right_ens_cyl-left_ens_cyl,toto_int);
	gTampon_appareil=image_appareil.getGraphics();
	tracker_appareil=new MediaTracker(this);
	tracker_appareil.addImage(image_appareil,1);
	if(interferometrie){
	    if(!(diffraction||perot_lame&&interferon!=lame_par_reflex))
		image_intensites=createImage(right_ens_cyl-(int)photom.pt.x,bottom_ens_cyl-top_ens_cyl);
	    else
		image_intensites=createImage(right_ens_cyl-(int)photom.pt.x+100,bottom_ens_cyl-top_ens_cyl);
	    gTampon_intensites=image_intensites.getGraphics();
	}
	tracker_appareil.addImage(image_appareil,2); 

	du_nouveau_a_voir=true;
    }
    void ouvre_classe(){
	if(i_demarre==60)
	    optic_geo=new un_dioptre_plan();
	if(i_demarre==61)
	    optic_geo=new dioptre_sph();
	else if(i_demarre==70)
	    optic_geo=new deux_dioptres_sph_similaires();
	else if(i_demarre==71)
	    optic_geo=new deux_dioptres_sph_opposes();
	else if(i_demarre==80)
	    optic_geo=new lentille_convergente();
	else if(i_demarre==81)
	    optic_geo=new lentille_divergente();
	else if(i_demarre==9)
	    optic_geo=new oeil();
	else if(i_demarre==10)
	    optic_geo=new microscope();
	else if(i_demarre==110)
	    optic_geo=new lunette_galilee();
	else if(i_demarre==111)
	    optic_geo=new jumelles();
	optic_geo.definit_faisceau_incident();
    }
    void va_chercher_et_affiche_explications(String namer){
	Image imager=createImage(380,340);
	Graphics gTTamponr=imager.getGraphics();
	imager=Toolkit.getDefaultToolkit().getImage(namer);
	//MediaTracker trackerr=new MediaTracker(this);
	trackerr.addImage(imager,1); 
	System.out.println(" namer "+namer);
	try {trackerr.waitForAll(); }
	catch (InterruptedException e){
	    System.out.println(" image2 pas arrivee?");
	}
	//g.drawImage(image2,555,0,null);
	toto_int=0;
	for(int ik=0;ik<10000;ik++)
	    toto_int++;
	if(interferometrie)
	    plaque_photo.grp_repr.drawImage(imager,10,30,subject.ensemble[i_ens]);
	else
	    grp_c.drawImage(imager,10,50,subject.ensemble[i_ens]);
	gTTamponr.dispose();
	imager.flush();
    }
    abstract class objectt{
	//nature_objet:0 pour source,1 pour laser,2 pour lentille,3 pour miroir plan,4 pour miroir sphèrique,5 pour fentes,}
	//6 pour lame semireflechissante,lame_a_faces_paralleles=7,8 pour bi_miroir,9 pour PM
	boolean leger_coin=false;double ang_coin=0.;
	int nature=-1,num_obj=0,nb_pt_a_deplacer=0;
	point pt,pt0;point center,center0;
	double indice_gauche=0.,indice_droite=0.;
	boolean inversion_d_indice=false;
	double coefff=1.e20;point u_normale_face_penchee;
	double carac=0.,carac2=0.,carac3=0.;
	point point_a_deplacer[]=new point [20];
	double largeur_fente_en_pixels,largeur_fente_en_pixels0;int nfentes=2;//pour les fentes
	double yfente_en_pixels[]=new double[20];
	double distance_des_fentes_en_pixels=1.;//pour les fentes
	double indice=1.;//sauf pour lame semi_reflechissante
	double pouvoir_reflecteur_en_amplitude_externe,pouvoir_reflecteur_en_amplitude_interne;//pour fabry perot et lame
	double pouvoir_miroir_reflecteur_en_amplitude_externe,pouvoir_miroir_reflecteur_en_amplitude_interne;//pour fabry perot et lame
	double pouvoir_transmetteur_en_amplitude_externe,pouvoir_transmetteur_en_amplitude_interne;//pour fabry perot et lame
	double pouvoir_miroir_transmetteur_en_amplitude_externe,pouvoir_miroir_transmetteur_en_amplitude_interne;//pour fabry perot et lame
	boolean a_peu_pres_vertical=true,a_peu_pres_horizontal=false;//pour PM.
	double angle_vs_normale_au_banc=0.,longueur=0.;
	double x_sur_le_plan_aval=0.,y_sur_le_plan_amont=0.,y_sur_le_plan_aval=0.;//pour perot_fabry et lame_semi par transmission ou par reflexion
	double x_sur_le_plan_amont=0.;
	double sin_de_l_angle_initial=0.,cos_de_l_angle_initial=0.;//pour le laser
	objectt(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    nb_objets++;objet[nb_objets-1]=this;
	    pt=new point(ptx,pty);
	    pt0=new point(ptx,pty);
	    angle_vs_normale_au_banc=angle_vs_normale_au_banc1;
	    a_peu_pres_vertical=Math.abs(angle_vs_normale_au_banc)<0.3;
	    u_normale_face_penchee=new point(0.,0.);
	    if(a_peu_pres_vertical)
		u_normale_face_penchee.assigne(1.,0.);
	    a_peu_pres_horizontal=Math.abs(angle_vs_normale_au_banc-pi/2.)<0.3;
	    if(a_peu_pres_horizontal)
		u_normale_face_penchee.assigne(0.,1.);
	    if(Math.abs(angle_vs_normale_au_banc-pi/4.)<0.01)
		u_normale_face_penchee.assigne(sqrt2/2.,sqrt2/2.);
	    if(Math.abs(angle_vs_normale_au_banc-pi/2.)>1e-10)
		coefff=-1./Math.tan(angle_vs_normale_au_banc);
	    longueur=longueur1;
	    nature=nature1;
	    carac=carac1;
	    carac2=carac21;
	    carac3=carac31;
	    pt.print("nature "+nature+" carac "+(float)carac+" obj pr");
	    num_obj=num_obj1;
	    System.out.println("dans objects num_obj "+num_obj);
	    for(int i=0;i<20;i++)
		point_a_deplacer[i]=new point(zer);
	}
	void calculs_face_penchee(double ang,boolean horiz){
	    ang_coin=ang;
	    if(Math.abs(ang)>0.000001)
		if(!horiz)
		    coefff=-1./Math.tan(ang);
		else
		    coefff=-Math.tan(ang);
	    else
		coefff=-1./1.e-12;
	    if(!horiz)
		u_normale_face_penchee.assigne(Math.cos(ang),Math.sin(ang));
	    else
		u_normale_face_penchee.assigne(Math.sin(ang),Math.cos(ang));
	}
	void  met_du_rouge(){
	    for( int iq=0;iq<nb_pt_a_deplacer;iq++)
		point_a_deplacer[iq].print(" iq "+iq+"nature "+nature+" point_a_deplacer[iq]");
	    for(int i=0;i<nb_pt_a_deplacer;i++){
		if(faisceau_incident_parallele&&(num_obj==num_obj_source&&i==0||num_obj!=num_obj_source)||!faisceau_incident_parallele){
		    toto.assigne(point_a_deplacer[i]);
		    for(int j=-2;j<3;j++)
			//if((i==0&&!(faisceau_incident_parallele&&nature==0)||nature==5))
			    subject.drawline_couleur(gTampon_appareil,(int)toto.x-2,(int)(int)toto.y+j,(int)toto.x+2,(int)(int)toto.y+j,Color.red);
		    //else
		    //subject.drawline_couleur(gTampon_appareil,(int)toto.x-2,(int)(int)toto.y+j,(int)toto.x+2,(int)(int)toto.y+j,Color.orange);
		}
	    }		    
	}
	abstract void dessine();	
	abstract void consequence_deplacement(int num_dep,point pt_dep);
    }
    class source extends objectt{
	int  n_sources_y=1,n_sources_z=1;
	double rayon_diaphrag=0.;point position_diaphragme;
	source(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);	    
	    num_obj_source=num_obj;
	    //if(!(i_demarre==perot_fabry)&&!(i_demarre==lame_semireflechissante)){
	    position_diaphragme=new point(zer);
	    if(!faisceau_incident_parallele){
		if(!(diffraction||perot_lame))
		    nb_pt_a_deplacer=1;
		else
		    nb_pt_a_deplacer=0;
		point_a_deplacer[0].assigne((double)ptx,(double)pty);
		if(michelsonne){
		    position_diaphragme.assigne(pt.x,pt.y+30);
		    rayon_diaphrag=Math.round(30*angle_limite_initial);
		}else{
		    position_diaphragme.assigne(pt.x+30,pt.y);
		    if(lame_reflexion)
		    	position_diaphragme.y+=15;
		    rayon_diaphrag=Math.round(15.);		
		}
	    }else{
		nb_pt_a_deplacer=3;
		for(int i=0;i<3;i++)// cela sera change dans definit.diaphragme()
		    point_a_deplacer[i].assigne(12.,y_banc+20*(i+1));
	    }
	}
	void dessine(){
	    for(int i=-n_sources_y;i<=n_sources_y;i++){
		if(!interferometrie){
		    if(faisceau_incident_parallele){
			    subject.drawline_couleur(gTampon_appareil,(int)point_a_deplacer[0].x-2,(int)point_a_deplacer[0].y+i,(int)point_a_deplacer[0].x+2,(int)point_a_deplacer[0].y+i,couleur_lumiere);
			    }
		    else if(i_demarre==microscope)
			subject.drawline_couleur(gTampon_appareil,(int)pt.x+i,(int)pt.y,(int)pt.x+i,(int)y_banc,couleur_lumiere);
		    else
			subject.drawline_couleur(gTampon_appareil,(int)pt.x+i,(int)pt.y-n_sources_y,(int)pt.x+i,(int)pt.y+n_sources_y,couleur_lumiere);
		    dessine_diaphragme();
		}else{
		    for(int j=-2;j<3;j++)
			if(diffraction) 
			    subject.drawline_couleur(gTampon_appareil,(int)pt.x-2,(int)(y_banc+(pt.y-y_banc)*echelle_transverse)+j+i,(int)pt.x+2,(int)(y_banc+(pt.y-y_banc)*echelle_transverse)+j+i,couleur_lumiere);
			else if(michelsonne)
			    subject.drawline_couleur(gTampon_appareil,(int)pt.x+i,(int)(pt.y)+j,(int)pt.x+i,(int)(pt.y)+j,couleur_lumiere);
			else
			    subject.drawline_couleur(gTampon_appareil,(int)pt.x-2+j,(int)pt.y+i,(int)pt.x+2+j,(int)pt.y+i,couleur_lumiere);
		    dessine_diaphragme();
		}
	    }
	}
	void dessine_diaphragme(){
	    tutu.assigne(position_diaphragme);
	    for(int j=-2;j<3;j++)// dessin du diaphragme
		if(michelsonne){
		    dodo=rayon_diaphrag;
		    if(source_etendue)
			dodo*=2;
		    subject.drawline_couleur(gTampon_appareil,(int)(tutu.x-dodo-15.),(int)(tutu.y)+j,(int)(tutu.x-dodo),(int)(tutu.y)+j,Color.black);
		    subject.drawline_couleur(gTampon_appareil,(int)(tutu.x+dodo+15.),(int)(tutu.y)+j,(int)(tutu.x+dodo),(int)(tutu.y)+j,Color.black);
		    //}else if(interferometrie){
		}else{
		    subject.drawline_couleur(gTampon_appareil,(int)(tutu.x+j),(int)(tutu.y-rayon_diaphrag-15.),(int)(tutu.x+j),(int)(tutu.y-rayon_diaphrag),Color.black);
		    subject.drawline_couleur(gTampon_appareil,(int)(tutu.x+j),(int)(tutu.y+rayon_diaphrag+15.),(int)(tutu.x+j),(int)(tutu.y+rayon_diaphrag),Color.black);
		}
	}
	void consequence_deplacement(int num_dep,point pt_dep){
	    if(interferometrie){
		if(!michelsonne)
		    pt.assigne(pt_dep.x,pt.y);
		else if(michelsonne){
		    //pt_dep.print(" pt0.x "+pt0.x+" pt_dep "); 
		    if(Math.abs(pt_dep.x-pt0.x)<40){
			pt.assigne(pt_dep.x,pt.y);
			angle_limite_initial=angle_limite_initial0*(lame_semi.pt0.y-pt0.y)/(lame_semi.pt0.y-pt0.y+pt.x-pt0.x);
			rayon_diaphrag=Math.round(30*angle_limite_initial);
			position_diaphragme.assigne(pt.x,pt.y+30);
		    }
		}else
		    position_diaphragme.assigne(pt.x+30,pt.y);
	    }else{
		if(faisceau_incident_parallele){
		    point_a_deplacer[num_dep].y=pt_dep.y;
		    for(int i=0;i<=2;i++){
			int j=i-num_dep;
			if(i==2)
			    j=-1;
			point_a_deplacer[i].y=point_a_deplacer[num_dep].y+j*rayon_diaphrag;			
		    }
		}else
		    pt.assigne(pt_dep);
	    }
	    if(interferometrie||(!interferometrie&&!faisceau_incident_parallele))
		point_a_deplacer[num_dep].assigne(pt);
	}
    }
    class lentille extends objectt{
	lentille(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
	    nb_pt_a_deplacer=1;
	    point_a_deplacer[0].assigne(pt);
	    if(!interferometrie){
		nb_pt_a_deplacer=3;
		point_a_deplacer[1].assigne(ptx+carac,pt.y);
		point_a_deplacer[2].assigne(ptx-carac,pt.y);
	    }
	}
	void corrections_points_to_move(){
	    point_a_deplacer[1].assigne(pt.x,pt.y+carac);
	    point_a_deplacer[2].assigne(pt.x,pt.y-carac);
	}
	void dessine(){
	    if(!vibration)
		System.out.println(" dans lentille dessine() a_peu_pres_vertical"+a_peu_pres_vertical);
	    paintcirclh((int)Math.round(pt.x),(int)Math.round(pt.y),4,Color.black);
	    if(a_peu_pres_vertical){
		paintcirclh((int)Math.round(pt.x-carac),(int)Math.round(pt.y),4,Color.black);
		paintcirclh((int)Math.round(pt.x+carac),(int)Math.round(pt.y),4,Color.black);
		toto.assigne(pt.x,pt.y-longueur);
		titi.assigne(pt.x,pt.y+longueur);
		drawlinj(toto,titi,Color.black);
		if(carac > 0) {
		    tata.assigne(pt.x-10,pt.y-longueur+10);
		    drawlinj(toto,tata,Color.black);		
		    tata.assigne(pt.x+10,pt.y-longueur+10);
		    drawlinj(toto,tata,Color.black);
		    
		    tata.assigne(pt.x-10,pt.y+longueur-10);
		    drawlinj(titi,tata,Color.black);		
		    tata.assigne(pt.x+10,pt.y+longueur-10);
		    drawlinj(titi,tata,Color.black);
		}else{
		    tata.assigne(pt.x-10,pt.y-longueur-10);
		    drawlinj(toto,tata,Color.black);
		    tata.assigne(pt.x+10,pt.y-longueur-10);
		    drawlinj(toto,tata,Color.black);
		    
		    tata.assigne(pt.x-10,pt.y+longueur+10);
		    drawlinj(titi,tata,Color.black);		
		    tata.assigne(pt.x+10,pt.y+longueur+10);
		    drawlinj(titi,tata,Color.black);
		}
	    }else{
		paintcirclh((int)Math.round(pt.x),(int)Math.round(pt.y-carac),4,Color.black);
		paintcirclh((int)Math.round(pt.x),(int)Math.round(pt.y+carac),4,Color.black);
		toto.assigne(pt.x-longueur,pt.y);
		titi.assigne(pt.x+longueur,pt.y);
		drawlinj(toto,titi,Color.black);
		if(carac > 0) {
		    tata.assigne(pt.x-longueur+10,pt.y-10);
		    drawlinj(toto,tata,Color.black);		
		    tata.assigne(pt.x-longueur+10,pt.y+10);
		    drawlinj(toto,tata,Color.black);
		    
		    tata.assigne(pt.x+longueur-10,pt.y-10);
		    drawlinj(titi,tata,Color.black);		
		    tata.assigne(pt.x+longueur-10,pt.y+10);
		    drawlinj(titi,tata,Color.black);
		}else{
		    tata.assigne(pt.x-longueur+10,pt.y-10);
		    drawlinj(toto,tata,Color.black);
		    tata.assigne(pt.x-longueur+10,pt.y+10);
		    drawlinj(toto,tata,Color.black);
		    
		    tata.assigne(pt.x+longueur+10,pt.y-10);
		    drawlinj(titi,tata,Color.black);		
		    tata.assigne(pt.x+longueur+10,pt.y+10);
		    drawlinj(titi,tata,Color.black);
		}
	    }
	}
	void consequence_deplacement(int num_dep,point pt_dep){
	    if(num_dep==0){
		if(interferometrie){
		    pt.x=pt_dep.x;
		    carac=photom.pt.x-pt.x;
		}else{
		    pt.x=pt_dep.x;
		    //System.out.println("lentle_3 "+lentle_3+" num_obj "+num_obj+" objet[num_obj] "+objet[num_obj]);  
		    if(objet[num_obj]==lentle_2&&lentle_3!=null){
			lentle_3.pt.x=lentle_3.pt0.x+pt.x-pt0.x;
			System.out.println(" lentle_3.pt.x "+lentle_3.pt.x+" lentle_3.pt0.x "+lentle_3.pt0.x+" pt.x "+pt.x+" pt0.x "+pt0.x);
		    }
		}
	    }else if (num_dep==1){
		if(a_peu_pres_vertical)
		    carac=pt_dep.x-pt.x;
		else
		    carac=pt_dep.y-pt.y;
	    }else if (num_dep==2){
		if(a_peu_pres_vertical)
		    carac=-(pt_dep.x-pt.x);
		else
		    carac=-(pt_dep.y-pt.y);
	    }
	    point_a_deplacer[0].assigne(pt);
	    if(a_peu_pres_vertical){
		point_a_deplacer[1].assigne(pt.x+carac,pt.y);
		point_a_deplacer[2].assigne(pt.x-carac,pt.y);
	    }else{
		point_a_deplacer[1].assigne(pt.x,pt.y+carac);
		point_a_deplacer[2].assigne(pt.x,pt.y-carac);
	    }
	}
    }
    class miroir_plan extends objectt{
	miroir_plan(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
	    nb_pt_a_deplacer=0;
	    //point_a_deplacer[0].assigne(pt);
	    if(angle_vs_normale_au_banc!=0.)
		if(a_peu_pres_vertical)
		    coefff=-1./Math.tan(angle_vs_normale_au_banc);
		    //coefff=1./Math.tan(angle_vs_normale_au_banc);
		else
		    coefff=-Math.tan(angle_vs_normale_au_banc);
	}
	void dessine(){
	    if(a_peu_pres_vertical){
		if(!((interf_mich.lame_mais_miroirs_non_perp||interf_mich.miroir_gauche_deregle)&&num_obj==num_obj_miroir_gauche)){
		    toto.assigne(pt.x,pt.y-longueur);
		    titi.assigne(pt.x,pt.y+longueur);
		    drawlinj(toto,titi,Color.black);
		}else{
		    sisin=Math.sin(angle_vs_normale_au_banc);
		    toto.assigne(pt.x,pt.y);		    
		    titi.assigne(pt.x+longueur*sisin,pt.y-longueur);
		    drawlinj(toto,titi,Color.black);
		    titi.assigne(pt.x-longueur*sisin,pt.y+longueur);
		    drawlinj(toto,titi,Color.black);
		}
	    }else{
		if(!((interf_mich.lame_mais_miroirs_non_perp||interf_mich.miroir_bas_deregle)&&num_obj==num_obj_miroir_bas)){
		    toto.assigne(pt.x-longueur,pt.y);
		    titi.assigne(pt.x+longueur,pt.y);		
		    drawlinj(toto,titi,Color.black);
		}else{
		    sisin=Math.sin(angle_vs_normale_au_banc);
		    toto.assigne(pt.x,pt.y);		    
		    titi.assigne(pt.x+longueur,pt.y-longueur*sisin);
		    drawlinj(toto,titi,Color.black);
		    titi.assigne(pt.x-longueur,pt.y+longueur*sisin);
		    drawlinj(toto,titi,Color.black);
		}
	    }	  
	}
	void consequence_deplacement(int num_dep,point pt_dep){
	    pt.assigne(pt_dep);
	    point_a_deplacer[0].assigne(pt);
	}
    }
    void ecrit_message_important(String imposs){
	if(plaque_photo!=null){
	    subject.eraserect(plaque_photo.grp_repr,0,0,400,400,Color.white);
	    plaque_photo.grp_repr.setColor(Color.red);
	    plaque_photo.grp_repr.setFont(subject.times_gras_24);
	    plaque_photo.grp_repr.drawString(imposs,10,200);
	    plaque_photo.grp_repr.setFont(subject.times14);
	}else{
	    subject.eraserect(grp_c,0,0,400,400,Color.white);
	    grp_c.setColor(Color.red);
	    grp_c.setFont(subject.times_gras_24);
	    grp_c.drawString(imposs,10,200);;
	    grp_c.setFont(subject.times14);
	}
    }
    class spectre_couleur extends Frame{
	Graphics grfic;
	float intensite_couleur[][]=new float [301][1];
	double intens_norme=0.;
	int top,left,bottom,right;
	spectre_couleur(String s){
	    super(s);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			/*
			while(occupied){
			}
			*/
			sp_couleur=null;
			dispose();
		    };
		});		
	    top=90;left=430;bottom = 300;right = 750;
	    pack();
	    setSize(right-left,bottom-top);
	    setLocation(left,top+i_ens*300);
	    grfic=getGraphics ();
	    grfic.setColor(Color.blue);
	}
	public void dessine(){
	    setVisible(true);
	    subject.eraserect(grfic,0,0,500,500,Color.white);
	    //grfic.setColor(Color.red);grfic.fillRect(0,0,500,500);
	    int inte=0,j=0;
	    for (int i=0;i<300;i++){
		inte=(int)Math.round(80.*intensite_couleur[i][0]/int_norm_monochr);
		if(diffraction)
		    j=i/3;
		else if (perot_lame||michelsonne)
		    j=indice_lamb;
		if(i/10*10==i)
		    System.out.println("i "+i+" j"+j+" inte "+inte+" intensite_couleur[i][0] "+(float)intensite_couleur[i][0]);
		grfic.setColor(farbe_bei_laenge[j].col);
		if(j>=0)
		    subject.drawline_couleur(grfic,10+i,150-inte,10+i,150,farbe_bei_laenge[j].col);
	    }
	    grfic.setColor(Color.black);
	    grfic.drawString(""+Math.round(lambda_min*1e9)/1e5+"µ",10,170);		
	    grfic.drawString(" Longueur d'onde ",150,170);		
	    grfic.drawString(""+Math.round(lambda_max*1e9)/1e5+"µ",260,170);		
	    //objet[208]=null;
	}	    
    }
    abstract class optique_geometrique{
	dioptre_spherique dipsph,dipsph1,dipsph2,dipsph3,retina;
dioptre_plan diptr,diptr1;
	int n_rayons_geom=3;point point_image;
	double coef_rayon[]=new double[3];
	point_coeff pc_trouve[]=new point_coeff [3];
	point_coeff pc_final,pc_trouve_garde,pc_toto;
	MenuItem iteoeil,faisc_parallele,itep_vitre,itep_inversion_indices;
	boolean retour_au_faisceau_divergent=false;
	optique_geometrique(){
	    if(i_demarre==9||i_demarre==10)
		y_banc=400.;
	    src=new source(28,y_banc-10.,0.,0,la_source,0.,0.,0.,nb_objets);
	    itep_couleur.setEnabled(false);
	    if(i_demarre==9)
		iteoeil=new MenuItem("mettre un verre correctif");
	    else
		iteoeil=new MenuItem("ajouter une lentille pour figurer l'oeil");
	    if(i_demarre==60)
		itep_vitre=new MenuItem("rajouter un dioptre plan");
	    if(i_demarre==60||i_demarre==61)
		itep_inversion_indices=new MenuItem("inverser les zones verte et blanche");
	    faisc_parallele=new MenuItem("faisceau incident parallele");
	    point_image=new point(zer);
	    pc_final=new point_coeff(zer,0.);
	    pc_trouve_garde=new point_coeff(zer,0.);
	    pc_toto=new point_coeff(zer,0.);
	    for(int i=0;i<=2;i++)
		pc_trouve[i]=new point_coeff(zer,0.);
	}
	class dioptre_plan extends objectt{
	    dioptre_plan(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
		super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
		indice_gauche=carac2;indice_droite=carac3;
		nb_pt_a_deplacer=1;
		point_a_deplacer[0].assigne(pt);
	    }
	    void dessine(){
		paintcirclh((int)Math.round(pt.x),(int)Math.round(pt.y),4,Color.black);
		toto.assigne(pt.x,pt.y-longueur);
		titi.assigne(pt.x,pt.y+longueur);
		drawlinj(toto,titi,Color.black);
	    }
	    void consequence_deplacement(int num_dep,point pt_dep){
		pt.x=pt_dep.x;
	    }
	}
	class dioptre_spherique extends objectt{
	    point top_left;int demi_angle=0;
	    dioptre_spherique(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
		super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
		indice_gauche=carac2;indice_droite=carac3;
		center=new point(pt.x-carac,pt.y);
		center.print(" num_obj "+num_obj+" carac "+carac+" center ");
		center0=new point(center);
		top_left=new point(0.,0.);
		nb_pt_a_deplacer=2;
		point_a_deplacer[0].assigne(pt);
		point_a_deplacer[1].assigne(center);
		
	    }
	    void dessine(){
		paintcirclh((int)Math.round(pt.x),(int)Math.round(pt.y),4,Color.black);
		paintcirclh((int)Math.round(center.x),(int)Math.round(center.y),4,Color.black);
		top_left.assigne(center.x-Math.abs(carac),center.y-Math.abs(carac));	  
		demi_angle=(int)(Math.asin(longueur/Math.abs(carac))*180/pi+0.5);
		tata_int=-2*demi_angle;
		if(carac<0){
		    demi_angle=180-demi_angle;
		    tata_int=-tata_int;
		}
		top_left.print(" num_obj "+num_obj+" demi_angle "+demi_angle+" top_left ");
		gTampon_appareil.drawArc((int)(top_left.x+0.49),(int)(top_left.y+0.49),(int)(2*Math.abs(carac)+0.49),(int)(2*Math.abs(carac)+0.49),demi_angle,tata_int);    
	    }
	    void consequence_deplacement(int num_dep,point pt_dep){
		if(num_dep==0){
		    pt.x=pt_dep.x;
		    point_a_deplacer[0].assigne(pt);
		}else if(num_dep==1){
		    center.x=pt_dep.x;
		    point_a_deplacer[1].assigne(center);
		}
		carac=pt.x-center.x;
		if(objet[2]!=null)
		    if(objet[2].nature==le_dioptre_spherique)
			if(num_obj==1||num_obj==2){
			    objet[2].longueur=recalcule_la_hauteur_des_dioptres_spheriques();
			    if(i_demarre!=9)
				objet[1].longueur=objet[2].longueur;
			}
	    }
	}
	void definit_faisceau_incident(){
	    toto.assigne(objet[1].pt);
	    if(i_demarre==60&&diptr1==null||i_demarre==61)
		toto.x+=10;
	    else if(i_demarre==60&&objet[2].nature==le_dioptre_plan)
		toto.x=objet[2].pt.x+10;
	    else
		toto.x-=20;
	    src.position_diaphragme.assigne(toto);
	    src.rayon_diaphrag=20;
	    if(faisceau_incident_parallele){
		coco=-0.1;
		cucu=12.;
		cece=src.position_diaphragme.y-coco*(src.position_diaphragme.x-cucu);
		src.nb_pt_a_deplacer=3;
		for(int i=0;i<src.nb_pt_a_deplacer;i++){
		    int j=i-1;
		    if(j<0)
			j=2;
		    src.point_a_deplacer[j].assigne(cucu,cece+src.rayon_diaphrag*(i-1));
		}
	    }else if (retour_au_faisceau_divergent){
		src.nb_pt_a_deplacer=1;
		src.point_a_deplacer[0].assigne(src.pt);
	    }
	}
	void traite_comm_opt_geo(){
	    if(command=="ajouter une lentille pour figurer l'oeil"||command=="mettre un verre correctif"){
		ajout_lentille_oeil=true;
		iteoeil=null;
		double x_l=objet[nb_objets-1].pt.x+15.;
		if(i_demarre!=9){
		    iteoeil=new MenuItem("retirer la lentille qui figure l'oeil");
		    lentle_3=new lentille(x_l,y_banc,0.,50,la_lentille,25.,1.5,0.,nb_objets);
		}else{
		    iteoeil=new MenuItem("retirer le verre correctif");
		    x_l=src.position_diaphragme.x-40;
		    dipsph2=new dioptre_spherique(x_l,y_banc,0,250,le_dioptre_spherique,-400.,1.,1.5,nb_objets);
		    dipsph3=new dioptre_spherique(x_l+20,y_banc,0,250,le_dioptre_spherique,-360.,1.5,1.,nb_objets);
		}
		barre_des_menus();    

		//classe_les_objets_par_ordre_de_x_croissant();
		num_obj_lentille3=nb_objets-1;
		du_nouveau_a_voir=true;
		command="";
	    }
	    if(command=="retirer la lentille qui figure l'oeil"||command=="retirer le verre correctif"){
		ajout_lentille_oeil=true;
		iteoeil=null;
		if(i_demarre!=9){
		    iteoeil=new MenuItem("ajouter une lentille pour figurer l'oeil");
		    lentle_3=null;
		    nb_objets--;
		}else{
		    iteoeil=new MenuItem("mettre un verre correctif");
		    nb_objets-=2;
		    dipsph2=null; dipsph3=null;
		}
		barre_des_menus();
		du_nouveau_a_voir=true;
		command="";
	    }
	    if(command=="rajouter un dioptre plan"){
		nb_objets=1;
		diptr=null;objet[1]=null;
		diptr=new dioptre_plan(400.,y_banc,0,180,le_dioptre_plan,0.,1.,1.5,nb_objets);
		diptr1=new dioptre_plan(450.,y_banc,0,180,le_dioptre_plan,0.,1.5,1.,nb_objets);
		itep_vitre=new MenuItem("enlever le dioptre plan supplementaire");
		definit_faisceau_incident();
		iteoeil=null;
		iteoeil=new MenuItem("ajouter une lentille pour figurer l'oeil");
		barre_des_menus();
		du_nouveau_a_voir=true;
		command="";		
	    }
	    if(command=="enlever le dioptre plan supplementaire"){
		itep_vitre=new MenuItem("rajouter un dioptre plan");
		nb_objets=1;diptr=null;objet[1]=null;diptr1=null;objet[2]=null;
		diptr=new dioptre_plan(400.,y_banc,0,180,le_dioptre_plan,0.,1.5,1.,nb_objets);
		iteoeil=null;
		iteoeil=new MenuItem("ajouter une lentille pour figurer l'oeil");
		definit_faisceau_incident();
		barre_des_menus();
		du_nouveau_a_voir=true;
		command="";		
	    }
	    if(command=="faisceau incident parallele"){
		faisceau_incident_parallele=true;
		retour_au_faisceau_divergent=false;
		definit_faisceau_incident();
		faisc_parallele=null;
		faisc_parallele=new MenuItem("faisceau incident divergent d'un point");
		barre_des_menus();
		du_nouveau_a_voir=true;
		command="";
	    } 
	    if(command=="faisceau incident divergent d'un point"){
		faisceau_incident_parallele=false;
		retour_au_faisceau_divergent=true;
		faisc_parallele=null;
		faisc_parallele=new MenuItem("faisceau incident parallele");
		barre_des_menus();
		du_nouveau_a_voir=true;
		command="";
	    } 
	    
	}
	void trouve_et_dessine_image(){
	    System.out.println(" entree trouve_et_dessine_image ");
	    //objet[104]=null;
	    if(faisceau_incident_parallele){
		pc_trouve[0].pt.assigne(src.point_a_deplacer[0]);
		pc_trouve[0].coeff=(src.position_diaphragme.y-pc_trouve[0].pt.y)/(src.position_diaphragme.x-pc_trouve[0].pt.x);
		for(int i=1;i<=2;i++){
		    toto.assigne(src.point_a_deplacer[0]);
		    if(i==1)
			toto.y+=src.rayon_diaphrag;
		    else
			toto.y-=src.rayon_diaphrag;
		    pc_trouve[i].assigne(toto,pc_trouve[0].coeff); 
		}
	    }else
		for(int i=0;i<3;i++){
		    if(!(i==1&&Math.abs(src.pt.y)<src.rayon_diaphrag)){
			toto.assigne(src.position_diaphragme);
			toto.y-=(i-1)*src.rayon_diaphrag;
			toto.soustrait(src.pt);
			coef_rayon[i]=toto.y_sur_x();
		    }else
			coef_rayon[1]=0;
		    pc_trouve[i].assigne(src.pt,coef_rayon[i]);
		}
	    if(fin_deplacer_un_objet)
		fin_deplacer_un_objet=false;
	    int kq=0;
	    for(int i=0;i<3;i++){
		for(int jq=1;jq<nb_objets;jq++){
		    if(!(i_demarre==9&&jq==nb_objets-1)){
			kq=jq;
			if(i_demarre==9&&nb_objets==6)
			    if(jq==1||jq==2)
				kq=jq+3;
			    else 
				kq=jq-2;
			if(objet[kq].nature==la_lentille||objet[kq].nature==le_dioptre_spherique||objet[kq].nature==le_dioptre_plan){
			    pc_final.assigne(rayon_incident_coefficient_refracte(kq,pc_trouve[i]));
			    if(i==0)
				pc_final.print("kq "+kq+" pc_trouve[i].pt.x "+pc_trouve[i].pt.x+" pc_final "); 
			    drawlinj(pc_trouve[i].pt,pc_final.pt,couleur_lumiere);
			    pc_trouve[i].assigne(pc_final);
			    if(kq==1&&(i_demarre==10||i_demarre==111)){
				if(i==0)
				    pc_trouve_garde.assigne(pc_trouve[i]);
				if(i==1)
				    dessine_image(2);
			    }
			}
		    }
		}
	    }
	    dessine_image(3);
	}
	void dessine_image(int nb_rays){
	    System.out.println(" entree dessine_image nb_rays"+nb_rays);
	    for(int i=0;i<nb_rays;i++){
		if(nb_rays!=2||i!=0)
		    pc_toto.assigne(pc_trouve[i]);
		else if(i==0)
		    pc_toto.assigne(pc_trouve_garde);
		for(int j=i+1;j<nb_rays;j++){
		    point_image.assigne(pc_toto.croisement(pc_trouve[j]));
		    if(i_demarre==9||point_image.x>objet[nb_objets-1].pt.x||nb_rays==2)
			couleur_lumiere=Color.black;
		    else
			couleur_lumiere=Color.orange;
		    drawlinj(pc_toto.pt,point_image,couleur_lumiere);
		    drawlinj(pc_trouve[j].pt,point_image,couleur_lumiere);
		    System.out.println("i "+i+" j "+j+" pc_trouve[j].pt.x "+(float)pc_trouve[j].pt.x+" point_image.x "+(float)point_image.x+" pc_toto.pt.x "+(float)pc_toto.pt.x);
		    for(int k=-1;k<=1;k++){
			if(i_demarre==10|i_demarre==110||i_demarre==111)
			    subject.drawline_couleur(gTampon_appareil,(int)point_image.x+k,(int)point_image.y,(int)point_image.x+k,(int)y_banc,couleur_lumiere);
			for(int l=-2;l<3;l++)
			    subject.drawline_couleur(gTampon_appareil,(int)point_image.x-2,(int)(point_image.y)+l+i,(int)point_image.x+2,(int)(point_image.y)+l+i,couleur_lumiere);
			couleur_lumiere=Color.black;
			if(i_demarre!=9&&nb_rays!=2&&point_image.x<objet[nb_objets-1].pt.x){
			    toto.assigne(800.,pc_toto.pt.y+pc_toto.coeff*(800.-pc_toto.pt.x));
			    drawlinj(pc_toto.pt,toto,couleur_lumiere);
			    toto.assigne(800.,pc_trouve[j].pt.y+pc_trouve[j].coeff*(800.-pc_trouve[j].pt.x));
			    drawlinj(pc_trouve[j].pt,toto,couleur_lumiere);
			}
		    }
		}
	    }
	}
    }
    class un_dioptre_plan extends optique_geometrique{
	un_dioptre_plan(){
	    super();
	    diptr=new dioptre_plan(400.,y_banc,0,180,le_dioptre_plan,0.,1.,1.5,nb_objets);
	    //diptr=new dioptre_plan(400.,y_banc,0,180,le_dioptre_plan,0.,1.5,1.,nb_objets);
	}
    }
    class dioptre_sph extends optique_geometrique{
	dioptre_sph(){
	    super();
	    //dipsph=new dioptre_spherique(400.,y_banc,0,180,le_dioptre_spherique,-250.,1.5,1.,nb_objets);
	    dipsph=new dioptre_spherique(400.,y_banc,0,180,le_dioptre_spherique,-250.,1.,1.5,nb_objets);
	}
    }
    class deux_dioptres_sph_similaires extends optique_geometrique{
	deux_dioptres_sph_similaires(){
	    super();
	    src.pt.x+=150;
	    src.point_a_deplacer[0].x=src.pt.x;
	    dipsph=new dioptre_spherique(500.,y_banc,0,100,le_dioptre_spherique,180.,1.,1.5,nb_objets);
	    dipsph1=new dioptre_spherique(520.,y_banc,0,100,le_dioptre_spherique,160.,1.5,1.,nb_objets);
	    dipsph1.longueur=recalcule_la_hauteur_des_dioptres_spheriques();
	    dipsph.longueur=dipsph1.longueur;
	}
    }
    class deux_dioptres_sph_opposes extends optique_geometrique{
	deux_dioptres_sph_opposes(){
	    super();
	    dipsph=new dioptre_spherique(290.,y_banc,0,100,le_dioptre_spherique,-160.,1.,1.5,nb_objets);
	    dipsph1=new dioptre_spherique(360.,y_banc,0,100,le_dioptre_spherique,160.,1.5,1.,nb_objets);
	     dipsph1.longueur=recalcule_la_hauteur_des_dioptres_spheriques();
	     dipsph.longueur=dipsph1.longueur;
	}
    }

    double recalcule_la_hauteur_des_dioptres_spheriques(){
	toto.assigne_soustrait(objet[1].center,objet[2].center);
	toto.print("objet[1].center.x "+objet[1].center.x+" objet[2].center.x "+objet[2].center.x+" toto ");
	if(toto.longueur_carre()<Math.pow(Math.abs(objet[1].carac)+Math.abs(objet[2].carac),2)){
	    coco=Math.abs(Math.abs(objet[1].carac)+Math.abs(objet[2].carac)-Math.abs(toto.x));
	    cece=-(Math.abs(objet[1].carac)-Math.abs(objet[2].carac)-Math.abs(toto.x))*(Math.abs(objet[1].carac)+Math.abs(objet[2].carac)-Math.abs(toto.x))/(2*Math.abs(toto.x));
	    cucu=cece*(2*Math.abs(objet[1].carac)-cece);
	    System.out.println(" coco "+coco+" cece "+cece+" cucu "+cucu+" Math.sqrt(cucu) "+Math.sqrt(cucu));
	    return Math.sqrt(cucu);
	}
	return -1.;
    }
    class lentille_convergente extends optique_geometrique{
	lentille_convergente(){
	    super();
	    lentle_1=new lentille(200.,y_banc,0.,60,la_lentille,120.,0.,0.,nb_objets);
	    num_obj_lentille1=nb_objets-1;
	}
    }
    class lentille_divergente extends optique_geometrique{
	lentille_divergente(){
	    super();
		lentle_1=new lentille(200.,y_banc,0.,60,la_lentille,-150.,0.,0.,nb_objets);
		num_obj_lentille1=nb_objets-1;
	}
    }
    class oeil extends optique_geometrique{
	oeil(){
	    super();
	    faisceau_incident_parallele=true;
	    dipsph=new dioptre_spherique(240.,y_banc,0,250,le_dioptre_spherique,-250.,1.,1.5,nb_objets);
	    dipsph1=new dioptre_spherique(300.,y_banc,0,100,le_dioptre_spherique,400.,1.5,1.25,nb_objets);
	    dipsph1.longueur=recalcule_la_hauteur_des_dioptres_spheriques();

	    retina=new dioptre_spherique(240.+2*250,y_banc,0,250,le_dioptre_spherique,250.,1.25,1.,nb_objets);
	}
    }
    class microscope extends optique_geometrique{
	microscope(){
	    super();
	    lentle_1=new lentille(120.,y_banc,0.,80,la_lentille,70.,0.,0.,nb_objets);
	    num_obj_lentille1=nb_objets-1;
	    lentle_2=new lentille(476.,y_banc,0.,80,la_lentille,50.,0.,0.,nb_objets);
	    num_obj_lentille2=nb_objets-1;
	}
    }
    class lunette_galilee extends optique_geometrique{
	lunette_galilee(){
	    super();
	    faisceau_incident_parallele=true;
	    src.pt.assigne(-1000.,-1000.);
	    itep_couleur.setEnabled(false);
	    lentle_1=new lentille(200.,y_banc,0.,80,la_lentille,-160.,1.5,0.,nb_objets);
	    num_obj_lentille1=nb_objets-1;
	    lentle_2=new lentille(400.,y_banc,0.,80,la_lentille,180.,1.5,0.,nb_objets);
	    num_obj_lentille2=nb_objets-1;
	}
    }
    class jumelles extends optique_geometrique{
	jumelles(){
	    super();
	    faisceau_incident_parallele=true;
	    src.pt.assigne(-1000.,-1000.);
	    itep_couleur.setEnabled(false);
	    lentle_1=new lentille(200.,y_banc,0.,80,la_lentille,160.,1.5,0.,nb_objets);
	    num_obj_lentille1=nb_objets-1;
	    lentle_2=new lentille(460.,y_banc,0.,80,la_lentille,60.,1.5,0.,nb_objets);
	    num_obj_lentille2=nb_objets-1;
	}
    }
    abstract class interferometres{
	objectt obj_perot_lame;int ppv=0;
	double facteur_de_reflexion_en_puissance_externe=0.,facteur_de_reflexion_en_puissance_interne=0.;
	double facteur_de_transmission_en_puissance_externe=0.,facteur_de_transmission_en_puissance_interne=0;
	double facteur_de_reflexion_miroir_en_puissance_externe=0.,facteur_de_reflexion_miroir_en_puissance_interne=0.,facteur_de_transmission_miroir_en_puissance_externe=0.,facteur_de_transmission_miroir_en_puissance_interne=0.;
	double unite_z=0.;
	Image image_explique;
	boolean calculs_source_ponctuelle_couleurs_deja_faits=false;
	boolean calculs_source_etendue_couleurs_deja_faits=false;
	boolean ne_rien_dessiner=false,limiter_z_a_150=false;
	int ii_zmin=0;int ii_zmax=300;
	int x0_dessin=0,fact0_dessin=-100;
	int j_lim_bas=0,j_lim_haut=0;
	final String angle_coin_string[]=new String[5];
	double angle_coin0=0.;
	//float intensite_source_ponctuelle_par_couleur[][][]=new float [300][300][10];
	final double angle_coin[]=new double[6];
	final MenuItem angle_coin_item[]=new MenuItem[6];
	String s_etendue_[]=new String[5];
	MenuItem s_etendue[]=new MenuItem[5];
	int dim_s_etendue[]=new int[5];
	point_y_z toto_y_z,tata_y_z,titi_y_z;
	Graphics gTampon_explique;
	MediaTracker tracker_explique;
	amperes ampere;
	int z_ppmm=0,y_ppmm=0;	double sum_intens=0.;
	double int_max_pour_plot=0;
	boolean pouv_separa=false,dessine_morceau=false;
	boolean spectre_source_etendue_fait=false;
	boolean chgt_nb_lambda[]=new boolean[301];
	long nb_lambdas_diff[]=new long[301];
	long dchemin_en_lambda_prec=-10000,dchemin_en_lambda=-10000;
	double dchemin_en_lambda_centre=0.;
	double chemin0[]=new double [300];
	float intens_source_etendue[][]=new float [301][300];
	double intens_etendue_lumiere_composee[][]=new double[301][300];
	double intens_lumiere_composee[][]=new double[301][300];
	final double lambdap[]=new double[10];	
	final double omegap[]=new double[10];	
	abstract void va_chercher_les_coordonnees(int pt_y,int pt_z);
	abstract void calcule_surface_source_etendue();
	abstract void traite_command();
	abstract void intensites_de_base();
	abstract void pouvoir_separateur();
	abstract void explique(String explic);
	abstract double intensite_en_fonction_de_diff_chemins(double diff_chemins,double lam);
	abstract void gere_plot_intensites_1dim(int ptt_y);
	interferometres(){
	    interferometrie=true;
	    s_etendue_[0]="source 3*3 pixels";
	    s_etendue_[1]="source 5*5 pixels";
	    s_etendue_[2]="source 7*7 pixels";
	    s_etendue_[3]="source 9*9 pixels";
	    s_etendue_[4]="source 11*11 pixels";
	    for(int ik=0;ik<5;ik++)
		s_etendue[ik]=new MenuItem(s_etendue_[ik]);
	    if(i_demarre!=-1)
		sp_couleur=new spectre_couleur("Intensite f.de longueur d'onde.");
	    determiner_ii_z_min_max(i_demarre==-1);
	    toto_y_z=new point_y_z(0.,0.);
	    tata_y_z=new point_y_z(0.,0.);
	    titi_y_z=new point_y_z(0.,0.);
	    for(int pp_y=-150;pp_y<150;pp_y++)
		for(int pp_z=0;pp_z<300;pp_z++)
		    colrvb[pp_y+150][pp_z]=new triple_double(0.,0.,0.);
	    ampere=new amperes();
	}
	class amperes implements Cloneable{
	    float intens[][]=new float [301][300];
	    amperes(){
	    }
	    public Object clone(){
		try{
		    return super.clone();
		}
		catch (CloneNotSupportedException e){
		    return null;
		}
	    }
	}
	void initialise_dessin(){
	    System.out.println(" entree dans initialise_dessin ");
	    if(plaque_photo!=null&&!vibration)
		subject.eraserect(plaque_photo.grp_repr,0,0,400,1600,Color.white);
	    subject.eraserect(gTampon_intensites,0,0,400,1600,Color.white);
	    if(!vibration)
		System.out.println(" $$$$$$$$$$$$$entree dessin i_ens "+i_ens+" du_nouveau_a_voir "+du_nouveau_a_voir);
	    ecrire_bandeau(gTampon_appareil);
	    gTampon_appareil.setColor(Color.black);
	    if(diffraction){
		int iprof=2*diffr_int.iprofondeur+1;
		gTampon_appareil.drawString("profondeur fente:"+iprof+" largeur fente",20,bottom_ens_cyl-60);
	    }
	}
	void dessine_interferometre(){
	    if(!deplacer_un_objet){
		if(!vibration)
		    System.out.println(" kkkkkk seulement_pour_montrer_rayons "+seulement_pour_montrer_rayons);
		if(seulement_pour_montrer_rayons)
		    if(michelsonne){
			coco=-angle_limite_initial+2*angle_limite_initial/300.*(-140+150);
			interf_mich.calcul_des_chemins(-140,coco);
			coco=-angle_limite_initial+2*angle_limite_initial/300.*(140+150);
			interf_mich.calcul_des_chemins(140,coco);
		    }
		interferon.releve_courbe();
		if(!vibration){
		    System.out.println(" apres releve_courbe() ");
		    System.out.println(" int_norm_monochr "+(float)int_norm_monochr+" int_max_pour_plot "+(float)int_max_pour_plot);
		}
		gTampon_appareil.setColor(Color.black);
		coco=Math.round(cms_par_pixels*100*100.)/100.;
		if(coco>=1.)
		    gTampon_appareil.drawString("100pixels="+coco+"cm",150,60);
		else
		    gTampon_appareil.drawString("100pixels="+coco*10+"mm",150,60);
		subject.drawline_couleur(gTampon_appareil,151,65,250,65,Color.red);
		if(perot_lame){
		    coco=Math.round(100*cms_par_pixels*10/echelle_transverse*1000.);
		    if(coco<1000)
			gTampon_appareil.drawString("Sur l'ecran,100 pixels="+coco+"microns",10,bottom_ens_cyl-55);
		    else
			gTampon_appareil.drawString("A l'écran,100 pixels="+coco/1000.+"mm",10,bottom_ens_cyl-55);
		    gTampon_appareil.drawString("Image agrandie fact. "+(int)echelle_transverse,135,45);
		    coco=Math.abs(fab_lame_mich.obj_perot_lame.carac)*cms_par_pixels;
		    if(lame_reflexion){
			coco=Math.round(coco*10000.*10.)/10.;
			gTampon_appareil.drawString("Epaisseur "+(float)coco+"microns",10,bottom_ens_cyl-75);
		    }else if(lame_transmission){
			coco=Math.round(coco*10.*10.)/10.;
			gTampon_appareil.drawString("Epaisseur "+(float)coco+"mms",10,bottom_ens_cyl-75);
		    }else if(perot_fabryyyy){
			coco=Math.round(coco*10.)/10.;
			gTampon_appareil.drawString("Epaisseur "+(float)coco+"cms",10,bottom_ens_cyl-75);
		    }
		}else if(michelsonne)
		    if(lentle_2==null)
			gTampon_appareil.drawString("Image agrandie fact 2",135,80);
		    else
			gTampon_appareil.drawString("Image agrandie fact 20",135,80);
		else if(diffraction){
		    coco=Math.round(diffr_int.fnte.carac*cms_par_pixels*10000.);
		    gTampon_appareil.drawString("Epaisseur fentes"+(float)coco+"microns",10,bottom_ens_cyl-75);
		    if(diffr_int.fnte.carac3>0.1&&!diffr_int.fnte.une_fente_deplacee){
			coco=Math.round(diffr_int.fnte.carac2*cms_par_pixels*10000.);
			gTampon_appareil.drawString("Distance fentes"+(float)coco+"microns",10,bottom_ens_cyl-90);
		    }
		    gTampon_appareil.drawString("Dans le plan transverse,",115,85);
		    gTampon_appareil.drawString("grandissement fact.100",115,97);
		}
		gTampon_appareil.setColor(Color.black);
		gTampon_appareil.drawString("0.4µ",left2,bot2-32);
		gTampon_appareil.drawString("0.7µ",right2-10,bot2-32);
		subject.paintrect_couleur(gTampon_appareil,top2,left2,bot2,right2,Color.black);
		coco=Math.round(lambda*1e6);
		toto_string="0."+(int)coco+"µ";
		gTampon_appareil.drawString(toto_string,(left2+right2)/2-25,bot2+15);
		for(int i=0;i<100;i++)
		    subject.drawline_couleur(gTampon_appareil,left2+i,bot2,left2+i,top2,farbe_bei_laenge[i].col);
		ppv=left2+(int)Math.round((right2-left2)*(lambda-min2)/(max2-min2));
		subject.drawline_couleur(gTampon_appareil,ppv,bot2,ppv,top2,Color.black );
		if(interferon.pouv_separa&&sp_couleur!=null){
		    sp_couleur.dessine();
		    interferon.pouv_separa=false;
		}
	    }
	}
	
	void determiner_ii_z_min_max(boolean lim_z_150){
	    if(lim_z_150){
		ii_zmin=150;ii_zmax=151;
	    }else{
		ii_zmin=0;ii_zmax=300;		
	    }
	}
	void calcule_intensites_lumiere_composee(){
	    System.out.println(" entree dans calcule_intensites_lumiere_composee() ");
	    for(int pp_y=-149;pp_y<150;pp_y++)
		for(int pp_z=ii_zmin;pp_z<ii_zmax;pp_z++){
		    if(!calculs_source_ponctuelle_couleurs_deja_faits){
			intens_lumiere_composee[pp_y+150][pp_z]=0.;
			intens_etendue_lumiere_composee[pp_y+150][pp_z]=0.;
			colrvb[pp_y+150][pp_z].remise_a_zero();//la couleur sera la meme pour ponctuelle et etendue, mais evidemment les intensites ne seront pas les memes. Du moins dans un premier temps.
		    }else if(!calculs_source_etendue_couleurs_deja_faits)
			intens_etendue_lumiere_composee[pp_y+150][pp_z]=0.;
		}
	    lambdaq=lambda;
	    if(!calculs_source_ponctuelle_couleurs_deja_faits){//on a besoin des calculs de source ponctuelle pour la source etendue
		for(int ic=0;ic<nb_de_couleurs;ic++){
		    lambda=lambdap[ic];
		    ne_rien_dessiner=true;
		    if(!michelsonne)
			intensites_de_base();
		    for(int pp_y=-149;pp_y<150;pp_y++)
			for(int pp_z=ii_zmin;pp_z<ii_zmax;pp_z++){
			    if(michelsonne){
				aoao=interf_mich.intensite_en_fonction_de_diff_chemins(interf_mich.diff_de_ch_opt[150+pp_y][pp_z],lambdap[ic]);
			    }else{
				aoao=ampere.intens[pp_y+150][pp_z];
			    }
			    intens_lumiere_composee[pp_y+150][pp_z]+=aoao;
			    incremente_colrvb(farbe_bei_laenge,indice_lambda[ic],aoao,pp_y,pp_z);
			    if(pp_z==150&&pp_y==0){
				colrvb[pp_y+150][pp_z].print("ttttttttt ic "+ic+" aoao "+(float)aoao+"colrvb[pp_y+150][pp_z] ");
			    }
			}
		}
		for(int pp_y=-149;pp_y<150;pp_y++)
		    for(int pp_z=ii_zmin;pp_z<ii_zmax;pp_z++){
			    colrvb[pp_y+150][pp_z].divise(intens_lumiere_composee[pp_y+150][pp_z]);
			    if(pp_z==150&&pp_y==0){
				colrvb[pp_y+150][pp_z].print("mmmmmmmmmmmcolrvb[pp_y+150][pp_z] ");
			    }
		    }
	    calculs_source_ponctuelle_couleurs_deja_faits=true;
	    }
	    if(source_etendue){
		for(int pp_y=-149;pp_y<150;pp_y++)
		    for(int pp_z=ii_zmin;pp_z<ii_zmax;pp_z++){
			aoao=intensite_source_extended(pp_y,pp_z,-1);
			intens_etendue_lumiere_composee[pp_y+150][pp_z]=aoao;
			if(pp_y/30*30==pp_y&&pp_z==150)
			    System.out.println(" pp_y "+pp_y+" intens_etendue_lumiere_composee[pp_y+150][pp_z] "+(float)intens_etendue_lumiere_composee[pp_y+150][pp_z]);
		    }
		calculs_source_etendue_couleurs_deja_faits=true;
	    }
	    lambda=lambdaq;
	}
	void plot_intensites(point p_plot, int x0_dess,double fact0_dess,int ptt_y){
	    if(!(interferon==lame_par_reflex&&lentle_2==null&&(lame_par_reflex.pas_trouve[ptt_y+150]||ptt_y<-110)))
	    if(!source_etendue)
		if(!lumiere_composee){
		    drawline_plot(gTampon_intensites,x0_dess,p_plot.y,x0_dess+(fact0_dess-20.)*ampere.intens[150+ptt_y][150]/int_max_pour_plot,p_plot.y,couleur_utilisee.col);
		    if(!diffraction&&!vibration){
			//if(fab_lame_mich!=lame_par_reflex)
			    if(michelsonne||lame_reflexion&&lentle_2!=null)
				ecrit_dchemins_en_lambda(ptt_y,(int)p_plot.y,64);
			    else if(perot_lame&&lentle_2!=null)
				ecrit_dchemins_en_lambda(ptt_y,(int)p_plot.y,0);
		    }
		}else{
		    drawline_plot(gTampon_intensites,x0_dess,p_plot.y,x0_dess+fact0_dess*intens_lumiere_composee[150+ptt_y][150]/int_max_pour_plot,p_plot.y,couleur_utilisee.col);
		    /*
		    if(ptt_y/10*10==ptt_y&&perot_lame)
			System.out.println(" ds plot_inten ptt_y "+ptt_y+"intens_lumiere_composee[150+ptt_y][150] "+(float)intens_lumiere_composee[150+ptt_y][150]+" int_max_pour_plot "+(float)int_max_pour_plot+" x0_dess "+x0_dess+" fact0_dess "+(float)fact0_dess+" int_norm_lum_bl "+(float)int_norm_lum_bl);
		    */
		}
	    else{
		if(!lumiere_composee){
		    drawline_plot(gTampon_intensites,x0_dess,p_plot.y,x0_dess+fact0_dess*intens_source_etendue[150+ptt_y][150]/int_max_pour_plot,p_plot.y,couleur_utilisee.col);
		}else{
		    drawline_plot(gTampon_intensites,x0_dess,p_plot.y,x0_dess+fact0_dess*intens_etendue_lumiere_composee[150+ptt_y][150]/int_max_pour_plot,p_plot.y,couleur_utilisee.col);
		    if(ptt_y/10*10==ptt_y)
			System.out.println("ptt_y "+ptt_y+" int_max_pour_plot "+(float)int_max_pour_plot+" intens_etendue_lumiere_composee[150+ptt_y] "+(float)intens_etendue_lumiere_composee[150+ptt_y][150+ptt_y]);
		}
		//if(source_etendue&&ptt_y==-140){
		//  couleur_utilisee.print(" couleur_utilisee ");
		//objet[208]=null;
	    }
	}
	void traite_comm(){
	    if(command=="Au centre, intensite fonction de la longueur d'onde"){
		pouv_separa=true;
		intensite_norme=0.;
		if(sp_couleur==null)
		    sp_couleur=new spectre_couleur("Au centre, intensite en fonction de la longueur d'onde.");
		sp_couleur.setVisible(true);
		pouvoir_separateur();
		sp_couleur.intens_norme=intensite_norme;
		System.out.println("vers sp_couleur.dessine() sp_couleur.intens_norme"+sp_couleur.intens_norme);
		lambda=min2+50*(max2-min2)/100.;
		du_nouveau_a_voir=true;
		command="";
		//objet[207]=null;
	    }
	    if(command=="mettre une lentille sur le trajet final."){
		fab_lame_mich.mettre_ou_enlever_la_lentille_finale(true);
		barre_des_menus();
		command="";
		du_nouveau_a_voir=true;
	    }
	    if(command=="enlever la lentille sur le trajet final."){
		fab_lame_mich.mettre_ou_enlever_la_lentille_finale(false);
		barre_des_menus();
		command="";
		du_nouveau_a_voir=true;
	    }

	    for(int ik=0;ik<5;ik++)
		if(command==s_etendue_[ik]){
		    src.n_sources_y=1+ik;
		    src.n_sources_z=1+ik;
		    System.out.println(" src.n_sources_y "+src.n_sources_y);
		    if(michelsonne){
			//initialise_menu_modifier();
			michelson_deregle=false;
			interf_mich.revenir_aux_conditions_de_depart();
			interf_mich.matching_et_intensites_deja_calcules=true;
			interf_mich.resultats_f_d_angle_deja_calcules=true;
			//itep_coin.setEnabled(false);
			itep_miroir_gauche_deregle.setEnabled(false);
			itep_miroir_bas_deregle.setEnabled(false);
			//src.pt.assigne(src.pt0);
			menu_vib.setEnabled(true);
			if(michelson_deregle)
			    intensites_de_base_deja_calculees=false;
		    }
		    source_etendue=true;
		    command="";
		    du_nouveau_a_voir=true;
		    spectre_source_etendue_fait=false;
		    barre_des_menus();
		}
	    if(command=="source ponctuelle"){
		    source_etendue=false;
		    src.n_sources_y=1;
		    src.n_sources_z=1;
		    command="";
		    du_nouveau_a_voir=true;
		    barre_des_menus();
	    }
	    if(command=="lame"||command=="miroirs perpendiculaires"){
		fab_lame_mich.initialise_menu_modifier();
		if(michelsonne)
		    interf_mich.revenir_aux_conditions_de_depart();
		itep_coin.setEnabled(true);
		itep_miroir_gauche_deregle.setEnabled(true);
		itep_miroir_bas_deregle.setEnabled(true);
		menu_vib.setEnabled(true);
		itep_composee.setEnabled(true);
		angle_coin0=0.;
		if(perot_lame){
		    obj_perot_lame.calculs_face_penchee(0.,obj_perot_lame.a_peu_pres_horizontal);
		    obj_perot_lame.leger_coin=false;
		    faces_perot_lame_non_paralleles=false;
		}
		if(michelsonne)
		    interf_mich.lame_mais_miroirs_non_perp=false;
		barre_des_menus(); 
	    }
	    if(command=="spectre de raies, dominante bleue"||command=="spectre continu"||command=="2 raies, une bleue, une rouge"||command=="2 raies, une verte, une rouge"){
		explique_lumiere_composee=true;expliquer_vibrations=false;
		if(command=="spectre continu"&&(michelsonne||perot_lame)){
		    subject.eraserect(gTampon_intensites,0,0,400,400,Color.white);
		    gTampon_intensites.setColor(Color.red);
		    gTampon_intensites.drawString("spectre ",20,100);
		    gTampon_intensites.drawString("uniforme",20,120);
		    gTampon_intensites.drawString("et incolore",20,140);
		    try {
			tracker_appareil.waitForAll(); 
		    }
		    catch (InterruptedException e){
			System.out.println(" image pas arrivee?");
		    }
		    if(!perot_lame)
			grp_c.drawImage(image_intensites,(int)photom.pt.x-10,0,subject.ensemble[i_ens]);
		    else
			grp_c.drawImage(image_intensites,(int)photom.pt.x-100,0,subject.ensemble[i_ens]);
		    if(plaque_photo!=null)
			ecrit_message_important("spectre uniforme et incolore");
		}else{
		    lumiere_composee=true;
		    calculs_source_ponctuelle_couleurs_deja_faits=false;
		    if(!michelsonne)
			intensites_de_base_deja_calculees=false;
		    du_nouveau_a_voir=true;
		    if(command=="spectre de raies, dominante bleue"){
			nb_de_couleurs=5;
			coco=(max2-(max2-min2)/3-min2);
			cucu=min2+(max2-min2)/3;
			for(int ic=0;ic<nb_de_couleurs;ic++){
			    lambdap[ic]=cucu+(ic*10+5)*coco/(nb_de_couleurs*10);
			    indice_lambda[ic]=ic*10+5;
			}
		    }
		    if(command=="2 raies, une bleue, une rouge"){
			nb_de_couleurs=2;
			lambdap[0]=min2+25*(max2-min2)/100.;
			lambdap[1]=min2+75*(max2-min2)/100.;
			indice_lambda[0]=25;
			indice_lambda[1]=75;
			System.out.println(" lambdap[0] "+lambdap[0]+" lambdap[1] "+lambdap[1]);
		    }
		    if(command=="2 raies, une verte, une rouge"){
			nb_de_couleurs=2;
			lambdap[0]=min2+50*(max2-min2)/100.;
			lambdap[1]=min2+75*(max2-min2)/100.;
			indice_lambda[0]=50;
			indice_lambda[1]=75;
		    }
		    if(command=="spectre continu"){
			nb_de_couleurs=10;	 
			for(int ic=0;ic<nb_de_couleurs;ic++){
			    indice_lambda[ic]=ic*10+5;
			    lambdap[ic]=min2+indice_lambda[ic]*(max2-(max2-min2)/3-min2)/100.;
			}
		    }
		    for(int ic=0;ic<nb_de_couleurs;ic++)
			omegap[ic]=2*pi/lambdap[ic];
		}
		command="";
	    }
	    if(command=="Source de lumiere monochromatique"){
		lumiere_composee=false;
		explique_lumiere_composee=false;
		lambda=lambda0;
		couleur_de_la_lumiere.assigne(farbe_bei_laenge[50]);
		couleur_lumiere=couleur_de_la_lumiere.col;
		intensites_de_base_deja_calculees=false;
		if(michelsonne)
		    interf_mich.matching_et_intensites_deja_calcules=false;
		du_nouveau_a_voir=true;
		command="";
	    }
	    
	    if(command=="source ponctuelle"){
		System.out.println(" src.n_sources_y "+src.n_sources_y);
		if(michelsonne){
		    //initialise_menu_modifier();
		    interf_mich.revenir_aux_conditions_de_depart();
		    interf_mich.matching_et_intensites_deja_calcules=true;
		    interf_mich.resultats_f_d_angle_deja_calcules=true;
		    //itep_coin.setEnabled(false);
		    itep_miroir_gauche_deregle.setEnabled(true);
		    itep_miroir_bas_deregle.setEnabled(true);
		    //src.pt.assigne(src.pt0);
		    menu_vib.setEnabled(true);
		}
		src.n_sources_y=1;
		src.n_sources_z=1;
		source_etendue=false;
		command="";
		du_nouveau_a_voir=true;
		//intensites_de_base_deja_calculees=false;
		spectre_source_etendue_fait=false; 
		barre_des_menus(); 
	    }
	    
	    if(command=="Multiplier l'echelle du dispositif par 2"||command=="Diviser l'echelle du dispositif par 2"){
		boolean operation_faite=true;
		if(command=="Multiplier l'echelle du dispositif par 2"){
		    if(fact_cms_par_pixels<=4.&&!((diffraction||perot_lame)&&fact_cms_par_pixels>2.)){
			fact_cms_par_pixels*=2;
			if(Math.abs(fact_cms_par_pixels-8.)<0.01||diffraction&&Math.abs(fact_cms_par_pixels-4.)<0.1)
			    itep_size_plus.setEnabled(false);
			itep_size_moins.setEnabled(true);
			cms_par_pixels=cms_par_pixels0*fact_cms_par_pixels;
			if(michelsonne)
			    interf_mich.recalculer_les_chemins_optiques_en_cm_et_intensites(2.);
		    }else{
			operation_faite=false;
			comment="Echelle trop grande, operation non faite";
			ecrire_bandeau(grp_c);
		    }
		}
		if(command=="Diviser l'echelle du dispositif par 2"){
		    if(fact_cms_par_pixels>=1./8.){
			fact_cms_par_pixels/=2;
			if(Math.abs(fact_cms_par_pixels-1./16.)<0.01)
			    itep_size_moins.setEnabled(false);
			itep_size_plus.setEnabled(true);
			cms_par_pixels=cms_par_pixels0*fact_cms_par_pixels;
			if(michelsonne)
			    interf_mich.recalculer_les_chemins_optiques_en_cm_et_intensites(0.5);
		    }else{
			operation_faite=false;			
			comment="Echelle trop petite, operation non faite";
			ecrire_bandeau(grp_c);
		    }	    
		}
		if(operation_faite){
		    if(diffraction||perot_lame){
			intensites_de_base_deja_calculees=false;
		    }
		    du_nouveau_a_voir=true;
		}
		command="";
	    }
	}
	void ecrit_dchemins_en_lambda(int ptt_y,int yy,int xx){
	    if(ptt_y==0){
		gTampon_intensites.setColor(Color.red);
		gTampon_intensites.drawString(""+dchemin_en_lambda_centre,xx,yy+5);
	    }
	    /*
	    if(perot_lame&&(Math.abs(ptt_y)<40||chgt_nb_lambda[ptt_y+150]))
		System.out.println("nnnnnnnn ptt_y "+ptt_y+" chgt_nb_lambda[ptt_y+150] "+chgt_nb_lambda[ptt_y+150]);
	    */
	    if(chgt_nb_lambda[ptt_y+150]&&Math.abs(ptt_y)>6&&Math.abs(ptt_y)<148){
		//if(chgt_nb_lambda[ptt_y+150]&&Math.abs(ptt_y)<148){
		//System.out.println("nnnnnnnn ptt_y "+ptt_y+" xx "+xx+" yy "+yy);
		gTampon_intensites.setColor(Color.black);
		toto_long=nb_lambdas_diff[ptt_y+150];
		if(ptt_y>0)
		    toto_long++;
		gTampon_intensites.drawString(""+toto_long,xx,yy+5);
	    }
	}      
	void calcule_nb_lambda_diff(int ptt_y,double cece){
	    dchemin_en_lambda=(int)cece;
	    if(ptt_y==0)
		dchemin_en_lambda_centre=(int)Math.round(cece*10)/10.;
	    //if(ptt_y<0)
	    //	System.out.println("ptt_y "+ptt_y+" dchemin_en_lambda "+dchemin_en_lambda);
	    if(Math.abs(dchemin_en_lambda-dchemin_en_lambda_prec)!=0){
		if(dchemin_en_lambda_prec>-9000){
		    chgt_nb_lambda[ptt_y+150]=true;
		    nb_lambdas_diff[ptt_y+150]=dchemin_en_lambda;
		    
		    //System.out.println("ptt_y "+ptt_y+" nb_lambdas_diff[ptt_y+150] "+nb_lambdas_diff[ptt_y+150]);
		}
		dchemin_en_lambda_prec=dchemin_en_lambda;
	    }
	}
	void stop_vibration(){
	    vibration=false;
	    if(expliquer_vibrations){		
		interferon.explique("C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_vibrations.jpg");
		expliquer_vibrations=false;
	    }
	    barre_des_menus();
	    du_nouveau_a_voir=true;
	    intensites_de_base_deja_calculees=false;
	    int_norm_lum_bl=0;
	    int_norm_etendue_monochr=0;
	    int_norm_etendue_lum_bl=0;
	    fab_lame_mich.mettre_ou_enlever_la_lentille_finale(false);
	    if(michelsonne){
		interf_mich.resultats_f_d_angle_deja_calcules=false;
		interf_mich.matching_et_intensites_deja_calcules=false;
	    }
	    command="";
	}
	void interferometre_vibre(){
	    intensites_de_base_deja_calculees=false;
	    du_nouveau_a_voir=true;
	    n_vibrations++;
	    sisin=Math.sin(angle_vib);
	    if(michelsonne){
		if(n_vibrations==1){
		    interf_mich.resultats_f_d_angle_deja_calcules=false;
		    interf_mich.matching_et_intensites_deja_calcules=false;
		}else if(n_vibrations>=2){
		    if(n_vibrations==2)
			for(int ptt_y=-150;ptt_y<150;ptt_y++)
			    chemin0[150+ptt_y]=interf_mich.diff_de_ch_opt[150+ptt_y][150];
		    interf_mich.resultats_f_d_angle_deja_calcules=true;
		    interf_mich.matching_et_intensites_deja_calcules=true;
		    caca=2*ampl_vib*sisin;
		    for(int ptt_y=-150;ptt_y<150;ptt_y++){
			coco=chemin0[150+ptt_y]-caca;
			if(!lumiere_composee){
			    ampere.intens[150+ptt_y][150]=(float)interf_mich.intensite_en_fonction_de_diff_chemins(coco,lambda);
			if(interf_mich.ampere.intens[150+ptt_y][150]>intensite_norme)
			    intensite_norme=interf_mich.ampere.intens[150+ptt_y][150];
			}else
			    calcul_des_intensites_composees_vibrations(ptt_y,coco);
		    }
		}
	    }else if(perot_fabryyyy){
		System.out.println(" Perot. entree vibrations  n_vibrations "+n_vibrations);
		//if(n_vibrations==3)
		// objet[1000]=null;
		if(n_vibrations==1)
		    intensites_de_base_deja_calculees=false;
		else if(n_vibrations>=2){
		    intensites_de_base_deja_calculees=true;
		    if(n_vibrations==2)
			for(int ptt_y=-150;ptt_y<150;ptt_y++){
			    chemin0[150+ptt_y]=fab_lame_mich.diff_de_ch_opt[150+ptt_y][150];
			    //if(ptt_y/10*10==ptt_y)
			    //	System.out.println(" ptt_y "+ptt_y+" chemin0[150+ptt_y] "+chemin0[150+ptt_y]);
			}
		    caca=2*ampl_vib*sisin;
		    System.out.println(" n_vibrations "+n_vibrations+" caca "+(float)caca);
		    for(int ptt_y=-150;ptt_y<150;ptt_y++){
			//coco=chemin0[150+ptt_y]*(1.-2*ampl_vib*sisin);
			coco=chemin0[150+ptt_y]-caca;
			//if(ptt_y/10*10==ptt_y)
			//   System.out.println(" n_vibrations "+n_vibrations+" chemin0[150+ptt_y] "+(float)chemin0[150+ptt_y]+" coco "+(float)coco);
			if(!lumiere_composee){
			    aoao=obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne*(-0.707);
			    ampere.intens[150+ptt_y][150]=(float)(1./(1.+aoao*aoao-2.*aoao*Math.cos(coco*2*pi/lambda)));
			    /*
			    if(ptt_y/10*10==ptt_y)
				System.out.println(" n_vibrations "+n_vibrations+" aoao "+(float)aoao+" ampere.intens[150+ptt_y][150] "+ampere.intens[150+ptt_y][150]);
			    */
			}else{
			    calcul_des_intensites_composees_vibrations(ptt_y,coco);
			}
		    }
		}
	    }
	    angle_vib+=d_angle_vib/(ampl_vib/0.00001);
	    if(angle_vib>2*pi)
		angle_vib=0.000001;
	    //System.out.println("dans interferometre_vibre() angle_vib "+angle_vib);
	    if(cliquee||pressee){
		expliquer_vibrations=false;
		stop_vibration();
	    }	
	}

	void calcul_des_intensites_composees_vibrations(int pt_y,double cc){
	    lambdaq=lambda;
	    intens_lumiere_composee[pt_y+150][150]=0;
	    colrvb[pt_y+150][150].remise_a_zero();
	    //	if(pt_y/20*20==pt_y)
	    //	    System.out.println(" n_vibrations "+n_vibrations+" obj_perot_lame "+obj_perot_lame+" obj_perot_lame.pouvoir_reflecteur_en_amplitude_interne "+obj_perot_lame.pouvoir_reflecteur_en_amplitude_interne);
	    for(int ic=0;ic<nb_de_couleurs;ic++){
		lambda=lambdap[ic];
		if(perot_fabryyyy){
		    aoao=obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne*(-0.707);
		    aoao=(float)(1./(1.+aoao*aoao-2.*aoao*Math.cos(cc*omegap[ic])));
		}else if(michelsonne)
		    aoao=(float)interf_mich.intensite_en_fonction_de_diff_chemins(coco,lambda);	
		intens_lumiere_composee[pt_y+150][150]+=aoao;
		//if(pt_y/10*10==pt_y)
		//   System.out.println(" n_vibrations "+n_vibrations+" ic "+ic+" pt_y "+pt_y+" aoao "+aoao );
		incremente_colrvb(farbe_bei_laenge,indice_lambda[ic],aoao,pt_y,150);
	    }
	    //if(pt_y/10*10==pt_y)
	    //	System.out.println(" n_vibrations "+n_vibrations+" pt_y "+pt_y+" intens_lumiere_composee[pt_y+150][150] "+(float)intens_lumiere_composee[pt_y+150][150]);

	    colrvb[pt_y+150][150].divise(intens_lumiere_composee[pt_y+150][150]);
	    lambda=lambdaq;
	}
	couleur travaille_couleur(couleur t_c){
	    comrvb.assigne(t_c);
	    if(nb_de_couleurs==10)  
		comrvb.multiplie(trifacteur);
	    t_c.assigne(corrige_couleur_composee_par_color_max(comrvb));
	    couleur_rvb.assigne(t_c);
	    return renormalise_couleur_composee();
	}
	triple_double determine_trifacteur(){
	    couleur_composee[pointt_max+150].print(" couleur_composee[pointt_max] ");
	    color_max.assigne(couleur_composee[pointt_max+150]);
	    couleur_max_max=color_max.r;
	    if(color_max.v>couleur_max_max)couleur_max_max=color_max.v;
	    if(color_max.b>couleur_max_max)couleur_max_max=color_max.b;
	    color_max.print(" couleur_max_max "+couleur_max_max+"color_max ");
	    toto_trd.assigne(1.,1.,1.);
	    if(couleur_max_max!=color_max.r)
		if(color_max.r!=0)
		    toto_trd.r=couleur_max_max/color_max.r;
		else
		    toto_trd.r=0;
	    if(couleur_max_max!=color_max.v)
		if(color_max.v!=0)
		    toto_trd.v=couleur_max_max/color_max.v;	
		else
		    toto_trd.v=0;
	    if(couleur_max_max!=color_max.b)
		if(color_max.b!=0)
		    toto_trd.b=couleur_max_max/color_max.b;	
		else
		    toto_trd.b=0;
	    toto_trd.print(" toto_trd ");
	    return toto_trd;
	}
	triple_double corrige_couleur_composee_par_color_max(triple_double trp){
	    max_de_la_couleur=trp.r;
	    if(trp.v>max_de_la_couleur)max_de_la_couleur=trp.v;
	    if(trp.b>max_de_la_couleur)max_de_la_couleur=trp.b;
	    tri_db.assigne(trp);tri_db.multiplie(couleur_max_max/max_de_la_couleur);
	    return tri_db;
	}
	couleur renormalise_couleur_composee(){
	    max_de_la_couleur=couleur_rvb.r;
	    if(couleur_rvb.v>max_de_la_couleur)max_de_la_couleur=couleur_rvb.v;
	    if(couleur_rvb.b>max_de_la_couleur)max_de_la_couleur=couleur_rvb.b;
	    couleur_rvb.r=(int)(couleur_rvb.r*255./max_de_la_couleur);
	    couleur_rvb.v=(int)(couleur_rvb.v*255./max_de_la_couleur);
	    couleur_rvb.b=(int)(couleur_rvb.b*255./max_de_la_couleur);
	    //couleur_composee[po].print("max_de_la_couleur "+max_de_la_couleur+"couleur_composee[po] ");
	    return couleur_rvb;
	}
	void incremente_colrvb(couleur[] c,int ind,double inten,int pp_y,int pp_z){
	    colrvb[pp_y+150][pp_z].r+=c[ind].r*inten;
	    colrvb[pp_y+150][pp_z].v+=c[ind].v*inten;
	    colrvb[pp_y+150][pp_z].b+=c[ind].b*inten;
	}
	couleur intensite_couleur(double intensi,couleur col,double int_nrm_max){
	    intensite_relative=intensi/int_nrm_max;
	    trp_int.assigne_facteur(col,intensite_relative*1.2);
	    //if(source_etendue)
	    //    trp_int.print("pppp intensi "+(float)intensi+" int_nrm_max "+(float)int_nrm_max+" trp_int ");
	    //if(trp_int.r!=255&&trp_int.v!=255&&trp_int.b!=255){
	    if(trp_int.r>255){
		double rapp=255./trp_int.r;
		trp_int.assigne_facteur(trp_int,rapp);
	    }
	    if(trp_int.v>255){
		double rapp=255./trp_int.v;
		trp_int.assigne_facteur(trp_int,rapp);
	    }
	    if(trp_int.b>255){
		double rapp=255./trp_int.b;
		trp_int.assigne_facteur(trp_int,rapp);
	    }
	    //}
	    //if(source_etendue)
	    //	trp_int.print(" trp_int final ");	
	    return (new couleur(trp_int.r,trp_int.v,trp_int.b));
	}
	void sauve_les_intensites(int pty,float[][] intensi,int ptt_y_orig,double inte,int ptz){
	    ypr=ypoint[ptt_y_orig+pty];
	    intensi[ptt_y_orig+pty][ptz]=(float)inte;
	    if(!dessine_la_plaque&&(ptz==150||ptz==0)){
		if(intensite_norme < inte&&ptz==150)
		    intensite_norme=inte;
		if(Math.abs(pty)<=60&&intensite_max<inte){
		    pointt_max=pty;
		    intensite_max=inte;
		    //if(lumiere_composee)
		    //System.out.println(" pointt_max "+pointt_max);
		}
	    }
	}
	
	double intensite_source_extended(int pt_y,int pt_z,int ic){
	    if(!spectre_source_etendue_fait)
		interferon.calcule_surface_source_etendue();
	    cucu=0.;
	    for(dppp_y=-dpt_y_source_etendue;dppp_y<=dpt_y_source_etendue;dppp_y++)
		for(dppp_z=-dpt_z_source_etendue;dppp_z<=dpt_z_source_etendue;dppp_z++)
		//for(dppp_y=0;dppp_y<=dpt_y_source_etendue;dppp_y++)
		//for(dppp_z=0;dppp_z<=dpt_z_source_etendue;dppp_z++)
		    if(pt_y+150+dppp_y>0&&pt_y+150+dppp_y<300&&pt_z+dppp_z>0&&pt_z+dppp_z<300){
			if(!lumiere_composee)
			    cucu+=ampere.intens[pt_y+150+dppp_y][pt_z+dppp_z];
			else
			    cucu+=interferon.intens_lumiere_composee[pt_y+150+dppp_y][pt_z+dppp_z];
		    }
	    //if(pt_z/20*20==pt_z&&pt_y/20*20==pt_y)
	    return cucu;
	}
	void releve_courbe(){
	    if(!vibration)
		System.out.println("entree releve_courbe() intensites_de_base_deja_calculees "+intensites_de_base_deja_calculees+" diffraction "+diffraction+" michelsonne "+michelsonne);
	    if(interf_mich!=null&&!vibration)
		System.out.println("miroir_gauche.angle_vs_normale_au_banc "+miroir_gauche.angle_vs_normale_au_banc+" angle_coin0 "+interf_mich.angle_coin0);
	    coco=0.;
	    for(int i=0;i<1000;i++)//pour assurer qu'on est là assez longtemps pour recueillir une commande
		coco+=1;
	    if(!intensites_de_base_deja_calculees){
		for(int ptt_y=-149;ptt_y<150;ptt_y++)
		    chgt_nb_lambda[ptt_y+150]=false;
		if(diffraction||perot_lame){
		    interferon.calculs_source_ponctuelle_couleurs_deja_faits=false;
		    interferon.calculs_source_etendue_couleurs_deja_faits=false;
		    photom.dessine();
		}
		if(plaque_photo!=null&&!vibration)
		    ecrit_message_important("Attendez quelques secondes,svp.");       
		seulement_pour_montrer_rayons=false;
		interferon.intensites_de_base();
		intensites_de_base_deja_calculees=true;	
	    }
	    seulement_pour_montrer_rayons=true;
	    if(lumiere_composee){
	       if(!source_etendue&&!calculs_source_ponctuelle_couleurs_deja_faits||source_etendue&&!calculs_source_etendue_couleurs_deja_faits){
		   if(plaque_photo!=null&&!vibration)
		       ecrit_message_important("Attendez quelques secondes,svp.");
		   interferon.calcule_intensites_lumiere_composee();
	       }
		for(int ptt_y=-149;ptt_y<150;ptt_y++){		
		    if(!source_etendue){
			if(int_norm_lum_bl<intens_lumiere_composee[150+ptt_y][150]&&Math.abs(ptt_y)<60){
			    //System.out.println("ptt_y "+ptt_y+" intens_lumiere_composee[150+ptt_y] "+(float)intens_lumiere_composee[150+ptt_y][150]+" int_norm_lum_bl "+int_norm_lum_bl);
			    int_norm_lum_bl=intens_lumiere_composee[150+ptt_y][150];
			}    
			if(Math.abs(ptt_y)<=60&&intensite_max<intens_lumiere_composee[150+ptt_y][150]){
			    pointt_max=ptt_y;
			    intensite_max=intens_lumiere_composee[150+ptt_y][150];
			}
		    }else{
			if(int_norm_etendue_lum_bl<intens_etendue_lumiere_composee[ptt_y+150][150])
			    int_norm_etendue_lum_bl=intens_etendue_lumiere_composee[ptt_y+150][150];
		    }
		    couleur_composee[ptt_y+150].assigne((int)(colrvb[ptt_y+150][150].r+0.5),(int)(colrvb[ptt_y+150][150].v+0.5),(int)(colrvb[ptt_y+150][150].b+0.5));
		}
		trifacteur.assigne(determine_trifacteur());
		int_max_pour_plot=intensite_norme;
		for(int ptt_y=-150;ptt_y<150;ptt_y++){
		    toto_coul.assigne(couleur_composee[ptt_y+150]);
		    couleur_composee[ptt_y+150].assigne(travaille_couleur(toto_coul));
		}
	    }else if(source_etendue){
		int_norm_etendue_monochr=0.;
		for(int ptt_y=-150;ptt_y<150;ptt_y++){
		    intensite=intensite_source_extended(ptt_y,150,-1);
		    intens_source_etendue[ptt_y+150][150]=(float)intensite;
		    if(int_norm_etendue_monochr<intensite)
			int_norm_etendue_monochr=intensite;
		}
	    }
	    if(i_demarre!=-1){
		if(plaque_photo==null)
		    plaque_photo=new plaque_photographique("Vue de l'ecran",i_ens);
		if(!grande_taille&&!vibration)
			dessine_plaque(plaque_photo.grp_repr);
	    }
	    for(int ptt_y=-149;ptt_y<150;ptt_y++){
		if(!(interferon==lame_par_reflex&&lentle_2==null&&lame_par_reflex.pas_trouve[ptt_y+150]))
		if(lumiere_composee){
		    if(!source_etendue){
			couleur_utilisee.assigne(intensite_couleur(intens_lumiere_composee[ptt_y+150][150],couleur_composee[ptt_y+150],int_norm_lum_bl));
			/*
			if(ptt_y/10*10==ptt_y){
			    couleur_utilisee.print(" intens_lumiere_composee[ptt_y+150] "+(float)intens_lumiere_composee[ptt_y+150]+" couleur_utilisee ");
			    couleur_composee[ptt_y+150].print(" int_norm_lum_bl "+(float)int_norm_lum_bl+"couleur_composee[ptt_y+150]");
			}
			*/
			int_max_pour_plot=int_norm_lum_bl;
		    }else{
			couleur_utilisee.assigne(intensite_couleur(intens_etendue_lumiere_composee[ptt_y+150][150],couleur_composee[ptt_y+150],int_norm_etendue_lum_bl));
			int_max_pour_plot=int_norm_etendue_lum_bl;
		    }		
		}else if(!source_etendue){
		    couleur_utilisee.assigne(intensite_couleur(ampere.intens[ptt_y+150][150],couleur_de_la_lumiere,int_norm_monochr));
		    int_max_pour_plot=int_norm_monochr;
		    //if(ptt_y/10*10==ptt_y)
		    //	System.out.println("ptty "+ptt_y+" ypoint[150+ptt_y] "+" ampere.intens[150+ptt_y][150] "+(float)ampere.intens[150+ptt_y][150]+" int_norm_monochr "+(float)int_norm_monochr);
		    
		}else{
		    couleur_utilisee.assigne(intensite_couleur(intens_source_etendue[ptt_y+150][150],couleur_de_la_lumiere,int_norm_etendue_monochr));
		    int_max_pour_plot=int_norm_etendue_monochr; 
		}
		interferon.gere_plot_intensites_1dim(ptt_y);
		if(ptt_y<0&&lentle_2!=null)
		    if((michelsonne||perot_lame)&&vibration){
			plaque_photo.grp_repr.setColor(couleur_utilisee.col);
			plaque_photo.grp_repr.fillOval((int)plaque_photo.centre.x+ptt_y,(int)plaque_photo.centre.y+ptt_y,-2*ptt_y,-2*ptt_y);
		    }
	    }
	}
	void dessine_plaque(Graphics g){
	    subject.eraserect(g,0,0,400,400,Color.white);
	    g.setColor(Color.red);
	    if(michelsonne){
		if(lentle_2==null)
		    toto_string="2";
		else
		    toto_string="20";
		g.drawString("Image agrandie fact. "+toto_string,135,45);
	    }else if(perot_lame||diffraction)
		g.drawString("Image agrandie fact. "+(int)echelle_transverse,135,45);
	    if(sp_couleur!=null)
	    	sp_couleur.setVisible(false);
	    System.out.println("entree dans dessine_plaque source_etendue"+source_etendue+" lumiere_composee "+lumiere_composee);
	    dessine_la_plaque=true;
	    int pt_z=150,pp_y=0,pp_z=0;
	    for(int pt_y=-149;pt_y<150;pt_y++){
		if(!(interferon==lame_par_reflex&&lentle_2==null&&(lame_par_reflex.pas_trouve[pt_y+150]||pt_y<-110)))
		for(pt_z=ii_zmin;pt_z<ii_zmax;pt_z++){
		    interferon.va_chercher_les_coordonnees(pt_y,pt_z);	
		    if(lumiere_composee){
			coucoul.assigne((int)(colrvb[pt_y+150][pt_z].r+0.5),(int)(colrvb[pt_y+150][pt_z].v+0.5),(int)(colrvb[pt_y+150][pt_z].b+0.5));
			toto_coul.assigne(travaille_couleur(coucoul));
			couleur_rvb.assigne(toto_coul);
			if(!source_etendue){
			    coucoul.assigne(intensite_couleur(intens_lumiere_composee[pt_y+150][pt_z],couleur_rvb,int_norm_lum_bl));
			    if(pt_y/10*10==pt_y&&pt_z==150)
				coucoul.print("pt_y "+pt_y+" intens_lumiere_composee[pt_y+150][150] "+(float)intens_lumiere_composee[pt_y+150][150]+" int_norm_lum_bl "+(float)int_norm_lum_bl+"coucoul ");
			}else{
			    coucoul.assigne(intensite_couleur(intens_etendue_lumiere_composee[pt_y+150][pt_z],couleur_rvb,int_norm_etendue_lum_bl));
			    if(pt_y/10*10==pt_y&&pt_z==150)
				coucoul.print("pt_y "+pt_y+" intens_etendue_lumiere_composee[pt_y+150][150] "+(float)intens_etendue_lumiere_composee[pt_y+150][150]+" int_norm_etendue_lum_bl "+(float)int_norm_etendue_lum_bl+"coucoul ");
			}
			subject.drawline_couleur(g,z_ppmm,y_ppmm,z_ppmm,y_ppmm,coucoul.col);
		    }else if(source_etendue){
			intens_source_etendue[pt_y+150][pt_z]=(float)intensite_source_extended(pt_y,pt_z,-1);
			if(pt_y/20*20==pt_y&&pt_z==150)
			    System.out.println("pt_y "+pt_y+" intens_source_etendue[150+ptt_y][150]150] "+(float)intens_source_etendue[150+pt_y][150]+" int_norm_etendue_monochr "+int_norm_etendue_monochr);
			coucoul.assigne(intensite_couleur(intens_source_etendue[pt_y+150][pt_z],couleur_de_la_lumiere,int_norm_etendue_monochr));
			subject.drawline_couleur(g,z_ppmm,y_ppmm,z_ppmm,y_ppmm,coucoul.col);			
		    }else{
			intensite=ampere.intens[150+pt_y][pt_z];
			coucoul.assigne(intensite_couleur(intensite,couleur_de_la_lumiere,int_norm_monochr));
			subject.drawline_couleur(g,z_ppmm,y_ppmm,z_ppmm,y_ppmm,coucoul.col);
		    }
		}
	    }
	    dessine_la_plaque=false;
	}
    }
    abstract class fabry_perot_ou_lame_ou_mich extends interferometres{
	int nombre_de_rayons=0;int point_y=0,point_z=0;
	int i_rayon=0;
	int sens_pos_y=1,sens_pos_z=1,p_y_deb=0,p_z_deb=0,sens_positif=1;
	boolean lame_mais_miroirs_non_perp=false,lame_d_air=true;
	boolean logic=false,print_trouve=false;
	double sinus_interne_lame=0.,sinus_externe=0.,tg_interne_lame=0.,difference_chemin_optique=0.;
	double z_sur_sqrt_x2_plus_y2[]=new double[300];
	point_y_z position_y_z[][]=new point_y_z[300][300];
	point_y_z amplitude_source_ponctuelle[][]=new point_y_z[300][300];
	double y1_rayons_differents=0,y1_rayons_differentsm1=0,z1_rayons_differents=0,z1_rayons_differentsm1=0;
	double delta_z_lame[]=new double[300];
	String ou_j_en_suis="";
	double cos_theta_prime=0.,sin_theta_prime=0.,delte=0.;
	double diff_de_ch_opt[][]=new double [300][300];
	double rr=0.;
	double chemin_optique=0.,chemin_optique_initial=0.;
	point_y_z pt_y_z_a_matcher;
	point_y_z pt_y_z_final_gauche,pt_y_z_final_bas,pt_y_z_ecran;
	double y_a_matcher=0.,z_a_matcher=0.;
	boolean matching_et_intensites_deja_calcules=false,resultats_f_d_angle_deja_calcules=false;
	cos_dir c_dir,c_dir_garde,c_dir_init,c_dir_second;
	point pt_lame;point pt_B;point pt_lame_s;point pt_lame_miroir;
	double pc_zz=0.,pc_zz_garde=0.,pc_z_prec=0.,pc_z_prec_garde=0.;
	double pzz_initial=0.;
	double chemin_optique_avec_z=0.,chemin_optique_avec_z_garde=0.,chemin_optique_garde=0.;
	fabry_perot_ou_lame_ou_mich(){
	    super();
	    pt_B=new point(0.,0.);pt_lame=new point(0.,0.);pt_lame_s=new point(0.,0.);pt_lame_miroir=new point(0.,0.);
	    c_dir=new cos_dir(0.,0.,0.);c_dir_garde=new cos_dir(0.,0.,0.);
	    c_dir_init=new cos_dir(0.,0.,0.);c_dir_second=new cos_dir(0.,0.,0.);
	    pt_y_z_final_gauche=new point_y_z(0.,0.);pt_y_z_final_bas=new point_y_z(0.,0.);pt_y_z_ecran=new point_y_z(0.,0.);
	}
	void drawlinf_somme_z(point p1,point p2,cos_dir c_d,double indice,int j,int pty){
	    toto.assigne_soustrait(p1,p2);
	    pc_zz+=toto.longueur()*c_d.z_sur_longueur_x_y();
	    caca=Math.sqrt(toto.longueur_carre()+(pc_zz-pc_z_prec)*(pc_zz-pc_z_prec));
	    chemin_optique_avec_z+=(caca*indice);
	    if(!vibration){
		if(dessine_parcours&&j==150&&i_rayon<=5){//ne representer que 5 rayons pour le fabry-perot
		    //subject.drawline_couleur(grp_c,(int)Math.round(p1.x),(int)Math.round(p1.y),(int)Math.round(p2.x),(int)Math.round(p2.y),Color.red);
		    subject.drawline_couleur(gTampon_appareil,(int)Math.round(p1.x),(int)Math.round(p1.y),(int)Math.round(p2.x),(int)Math.round(p2.y),Color.red);
		}
		/*	
		if((j==30||j==150)&&(pty==0||pty==120)&&i_rayon<3&&i_demarre!=-1){
		    //if(pty==-36&&(j==65||j==38)){
		    System.out.println(" ou_j_en_suis "+ou_j_en_suis+" uuuuuuuuuuuu ");
		     p1.print_float(" j "+j+" pty "+pty+" i_rayon "+i_rayon+" facteur_d_amplitude "+(float)facteur_d_amplitude+" p1 ");
		     p2.print_float(" pc_zz "+(float)pc_zz+" chemin_optique_avec_z "+(float)chemin_optique_avec_z+" p2 ");
		     c_dir.print("pc_z_prec "+(float)pc_z_prec+" chemin_optique "+chemin_optique_avec_z*cms_par_pixels/lambda+" toto.longueur() "+(float)toto.longueur()+" c_dir ");
		}
		*/	
	    }
	    pc_z_prec=pc_zz;
	}

	void va_chercher_les_coordonnees(int pt_y,int pt_z){
	    y_ppmm=(int)Math.round(ypoint[150+pt_y]);
	    /*
	    if(pt_y/20*20==pt_y&&pt_z/20*20==pt_z){
		System.out.println("pt_y "+pt_y+ "pt z "+pt_z+"  y_ppmm "+ y_ppmm+" y_centre "+y_centre+" photom.x "+(float)photom.pt.x);
		//objet[223]=null;
	    }
	    */
	    if(michelsonne)
		//if(dessine_morceau)
		    z_ppmm=(int)plaque_photo.centre.x+pt_z-150;
	    else
		if(perot_lame)
		    //if(dessine_morceau)
			z_ppmm=(int)((plaque_photo.centre.x+(pt_z-150)));
	}
	double trouve_y(point_y_z p_ecran,point_y_z pp_y_z[][],int po,int j){
	    int ptt=0;
	    //for(ptt=ptt_deb_y;ptt<150;ptt++){
	    sens_positif=1;
	    if(p_y_deb<=-150)
		p_y_deb=-149;
	    if(p_y_deb>=150)
		p_y_deb=149;
	    if(pp_y_z[150+p_y_deb][j].y-p_ecran.y>0.)
		sens_positif=-1;
	    //if(print_trouve)
	    //System.out.println("eeeeeeeeeepo "+po+" j "+j+" p_y_deb "+p_y_deb+" sens_positif "+sens_positif+" p_ecran.y "+(float)p_ecran.y+" y_centre "+(float)y_centre); 
	    for(ptt=p_y_deb;ptt<150&&ptt>-149;ptt+=sens_positif){
		y1_rayons_differents=pp_y_z[150+ptt][j].y;
		y1_rayons_differentsm1=pp_y_z[150+ptt-1][j].y;
		//if(print_trouve)
		//    System.out.println(" ptt "+ptt+" y1_rayons_differents "+(float)y1_rayons_differents);
		if(y1_rayons_differents>y1_rayons_differentsm1)
		    logic=y1_rayons_differents-p_ecran.y>0.&&y1_rayons_differentsm1-p_ecran.y<=0.;
		else
		    logic=y1_rayons_differents-p_ecran.y<0.&&y1_rayons_differentsm1-p_ecran.y>=0.;

		if(logic){
		    coco=Math.abs((p_ecran.y-y1_rayons_differents)/(y1_rayons_differents-y1_rayons_differentsm1));
		    //if(ptt_deb_y<-149)
		    //ptt_deb_y=-149;
		    aoao=ptt-coco;
		    if(aoao<0)
			aoao-=0.00000000001;
		    return (aoao);
		}
	    }
	    return (-1000.);
	}
	double trouve_z(point_y_z p_ecran,point_y_z pp_y_z[][],int po,int j){
	    int ptt=0;
	    //for(ptt=ptt_deb_z;ptt<150;ptt++){
	    if(p_z_deb<1)
		p_z_deb=1;
	    if(p_z_deb>=300)
		p_z_deb=298;
	    sens_positif=1;
	    if(pp_y_z[150+po][p_z_deb].z-p_ecran.z>0.)
		sens_positif=-1;
	    if(print_trouve)
		System.out.println("eeeeeeeeeepo "+po+" j "+j+" p_z_deb "+p_z_deb+" sens_positif "+sens_positif+" p_ecran.z "+(float)p_ecran.z); 
	    for(ptt=p_z_deb;ptt<300&&ptt>1;ptt+=sens_positif){
		z1_rayons_differents=pp_y_z[150+po][ptt].z;
		z1_rayons_differentsm1=pp_y_z[150+po][ptt-1].z;
		if(print_trouve)
		    System.out.println(" ptt "+ptt+" z1_rayons_differents "+(float)z1_rayons_differents);

		if(z1_rayons_differents>z1_rayons_differentsm1)
		    logic=z1_rayons_differents-p_ecran.z>0.&&z1_rayons_differentsm1-p_ecran.z<=0.;
		else
		    logic=z1_rayons_differents-p_ecran.z<0.&&z1_rayons_differentsm1-p_ecran.z>=0.;

		/*
		if(((po==100&&j==50)||po==-100&&j==50||po==-101&&j==50||po==0&&j==150)&&!vibration){
		    System.out.println("ptt "+ptt+" p_ecran.z "+(float)p_ecran.z+" z1_rayons_differents "+(float)z1_rayons_differents);
		}
		*/
		if(logic){
		    coco=Math.abs((p_ecran.z-z1_rayons_differents)/(z1_rayons_differents-z1_rayons_differentsm1));
		    //ptt_deb_y=ptt-2;
		    //if(ptt_deb_y<-149)
		    //ptt_deb_y=-149;
		    aoao=ptt-coco;
		    if(aoao<0)
			aoao-=0.00000000001;
		    return (aoao);
		}
	    }
	    return (-1000.);
	}

	void additionne_amplitude(int p_y,int p_z,double fc,double faact,int po,int j,int ig,point_y_z amp){
	    amplitude_source_ponctuelle[p_y+150][p_z].additionne_point_facteur(amp,fc);
	    /*
	    if(Math.abs(p_y-90)<4&&p_z==150)
		System.out.println("jjjj po "+po+" j "+j+" p_y "+p_y+" p_z "+p_z+" cucu "+(float)cucu+" cece "+(float)cece+" caca "+(float)caca+" cici "+(float)cici+" faact "+(float)faact+" fc "+(float)fc+" ampl_carre "+(float)amplitude_source_ponctuelle[p_y+150][p_z].longueur_carre());
	    */
	}
	boolean distribue_amplitude_sur_les_voisins(point_y_z absc_y_z,double fct,int po,int j,int ig,point_y_z amp){
	    //print_trouve=(po==-140&&j==20);
	    if(fab_lame==fbr_prt&&!obj_perot_lame.leger_coin)
		cucu=(absc_y_z.y-y_banc)*echelle_transverse;
	    else
		cucu=trouve_y(absc_y_z,position_y_z,po,j);
	    point_y=(int)Math.round(cucu);
	    if(interferon==lame_par_reflex&&j==150&&ig<=4&&point_y>-999)
		lame_par_reflex.pas_trouve[point_y+150]=false;    
	    p_y_deb=po-sens_pos_y*5;
	    cece=cucu-point_y;
	    //point_z=150+pc_zz*echelle_transverse;
	    if(fab_lame==fbr_prt&&!obj_perot_lame.leger_coin)
		caca=absc_y_z.z*echelle_transverse+150.;
	    else
		caca=trouve_z(absc_y_z,position_y_z,po,j);
	    point_z=(int)Math.round(caca);
	    p_z_deb=j-sens_pos_z*5;
	    cici=caca-point_z;
	    //if((Math.abs(point_y-32)<=2||Math.abs(po-45)<=15)&&j==150&&obj_perot_lame.leger_coin){
	    //if(po==30&&j==150&&ig==2&&obj_perot_lame.leger_coin)
	    //	objet[233]=null;

	    //	    if(((po==100&&j==250)||po==-100&&j==50||po==-101&&j==50||Math.abs(po-7)<=1&&j==150)&&!vibration){
	    if(Math.abs(point_y)<=148){
		if(Math.abs(point_z-150)<=148){
		    toto_int=1;
		    if(cece<0)
		       toto_int=-1;
		    titi_int=1;
		    if(cici<0)
		       titi_int=-1;
		    cece=Math.abs(cece);
		    cici=Math.abs(cici);
		    additionne_amplitude(point_y,point_z,fct*(1.-cece)*(1.-cici),fct,po,j,ig,amp);
		    additionne_amplitude(point_y+toto_int,point_z,fct*(1.-cici)*cece,fct,po,j,ig,amp);
		    additionne_amplitude(point_y+toto_int,point_z+titi_int,fct*cece*cici,fct,po,j,ig,amp);
		    additionne_amplitude(point_y,point_z+titi_int,fct*(1.-cece)*cici,fct,po,j,ig,amp);
		    
		    
		}
		return true;
	    }else
		return false;
	}
	point intersection_avec_plan(point pobj,double coefgf,point_coeff pc){
	    //y_intersec=(pc.coeff*(pobj.y-pobj.x*coefff)-coefff*pc.pt.y)/(pc.coeff-coefff);
	    //x_intersec=(y_intersec-pc.pt.y)/pc.coeff;
	    //x_intersec=(pobj.y-pobj.x*coefgf-pc.pt.y)/(pc.coeff-coefgf);
	    //pobj.print(" coefgf "+(float)coefgf+" p_obj ");
	    x_intersec=(pobj.y-pobj.x*coefgf-pc.pt.y+pc.coeff*pc.pt.x)/(pc.coeff-coefgf);
	    y_intersec=coefgf*x_intersec+pobj.y-pobj.x*coefgf;
	    p_intersec.assigne(x_intersec,y_intersec);
	    return p_intersec;
	}
	point_y_z sortie_z_bis(int j,int po,boolean gch,point ppp){
	    //if(miroir_gauche_deregle&&j==1&&po/10*10==po)
	    //	ppp.print("po "+po+" j "+j+" ppp ");
	    if(lentle_2==null)
		coco=(photom.pt.x-ppp.x);
	    else
		coco=(lentle_2.pt.x-ppp.x);
	    tata.x=ppp.x+coco;	
	    tata.y=ppp.y+c_dir.y_sur_x()*coco;
	    drawlinf_somme_z(tata,ppp,c_dir,1.,j,po);

	    if(j==150&&dessine_parcours&&michelsonne&&i_demarre!=-1)
		interf_mich.dessin_agrandi(tata,ppp,gch);
	    if(lentle_2==null){
		toto_y_z.y=tata.y;
		toto_y_z.z=pc_zz;
		//	if(miroir_gauche_deregle&&j==1&&po/10*10==po)
		//    tata.print("po "+po+" j "+j+" tata ");
	    }else{
		titi.x=tata.x+lentle_2.carac;
		titi.y=lentle_2.pt.y+lentle_2.carac*c_dir.pt.y/c_dir.pt.x;
		pc_zz=lentle_2.carac*c_dir.z/c_dir.pt.x;
		if(j==150&&(michelsonne||i_rayon<=5))
		    drawlinh(titi,tata,Color.red);
		toto.assigne_soustrait(tata,lentle_2.pt);
		tata.assigne_soustrait(titi,lentle_2.pt);
		tata.soustrait(toto);
		coco=tata.scalaire(c_dir.pt)+(pc_zz-pc_z_prec)*c_dir.z;
		chemin_optique_avec_z+=coco;
		toto_y_z.y=titi.y;
		toto_y_z.z=pc_zz;
	    }
	    return toto_y_z;
	}
	point intersection_avec_pla(point pobj,double coefgf,point_coeff pc,int j){
	    //y_intersec=(pc.coeff*(pobj.y-pobj.x*coefff)-coefff*pc.pt.y)/(pc.coeff-coefff);
	    //x_intersec=(y_intersec-pc.pt.y)/pc.coeff;
	    //x_intersec=(pobj.y-pobj.x*coefgf-pc.pt.y)/(pc.coeff-coefgf);
	    if(dessine_parcours&&j==150){
		pobj.print("coefgf "+(float)coefgf+"pobj");
		pc.print(" pc ");
	    }
	    x_intersec=(pobj.y-pobj.x*coefgf-pc.pt.y+pc.coeff*pc.pt.x)/(pc.coeff-coefgf);
	    y_intersec=coefgf*x_intersec+pobj.y-pobj.x*coefgf;
	    p_intersec.assigne(x_intersec,y_intersec);
	    return p_intersec;
	}
	void recalculer_les_chemins_optiques_en_cm_et_intensites(double facteur_d_echelle){
	    int ptt_z=0;
	    if(michelsonne)
		interf_mich.resultats_f_d_angle_deja_calcules=true;
	    intensites_de_base_deja_calculees=true;
	    intensite_norme=0.;
	    if(perot_lame)
		cece=facteur_de_reflexion_en_puissance_externe;
	    dchemin_en_lambda_prec=-10000;
	    for(int ptt_y=-149;ptt_y<150;ptt_y++){
		chgt_nb_lambda[ptt_y+150]=false;
		for(ptt_z=ii_zmin+1;ptt_z<ii_zmax;ptt_z++){
		    diff_de_ch_opt[150+ptt_y][ptt_z]*=facteur_d_echelle;
		    interf_mich.ergebnis[150+ptt_y][ptt_z].gauche.chemin*=facteur_d_echelle;
		    interf_mich.ergebnis[150+ptt_y][ptt_z].bas.chemin*=facteur_d_echelle;
		    cucu=Math.abs(diff_de_ch_opt[150+ptt_y][ptt_z])/lambda;
		    if(ptt_z==150)
			calcule_nb_lambda_diff(ptt_y,cucu);
		    caca=(cucu-(int)cucu)*2*pi;
		    intensite=(1.+Math.cos(caca));
		    sauve_les_intensites(ptt_y,ampere.intens,150,intensite,ptt_z);
		    if(ptt_z==150)
			int_norm_monochr=intensite_norme;
		    
		}
	    }
	}
	void initialise_menu_modifier(){
	    itep_mettre_lentille_finale.setEnabled(true);
	    michelson_deregle=false;
	    du_nouveau_a_voir=true;
	    intensites_de_base_deja_calculees=false;
	    matching_et_intensites_deja_calcules=false;
	    resultats_f_d_angle_deja_calcules=false;
	    source_etendue=false;
	    command="";
	}
	void mettre_ou_enlever_la_lentille_finale(boolean mettre_lentille){
	    itep_mettre_lentille_finale=null;
	    if(mettre_lentille){
		if(michelsonne)
		    lentle_2=new lentille(250.,y_banc,0.,60,la_lentille,photom.pt.x-250.,0.,0.,nb_objets);
		else
		    if(interferon==lame_par_reflex)
			lentle_2=new lentille(210.,y_banc,0.,100,la_lentille,photom.pt.x-210.,0.,0.,nb_objets);
		    else
			lentle_2=new lentille(190.,y_banc,0.,100,la_lentille,photom.pt.x-190.,0.,0.,nb_objets);
		num_obj_lentille2=nb_objets-1;		
		itep_mettre_lentille_finale=new MenuItem("enlever la lentille sur le trajet final.");
	    }else{
		lentle_2=null;nb_objets--;
		itep_mettre_lentille_finale=new MenuItem("mettre une lentille sur le trajet final.");
	    }
	    if(michelsonne)
		interf_mich.revenir_aux_conditions_de_depart();
	    intensites_de_base_deja_calculees=false;
	    matching_et_intensites_deja_calcules=false; 
	    resultats_f_d_angle_deja_calcules=false;
	}
	void explique(String explic){
	    System.out.println(" explique fabry_perot_ou_lame_ou_diff ");
	    if(explic==""){
		toto_string="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_diffraction.jpg";
		if(interferon!=lame_par_reflex){
		    toto_string="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_perot_lame.jpg";
		}else{
		    if(plaque_photo==null)
			plaque_photo=new plaque_photographique("Explications lame par reflexion",i_ens);
		    //if(plaque_photo_prime==null)
		    //plaque_photo_prime=new plaque_photographique("Vue de l'ecran",0);

		    toto_string="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_lame_par_reflexion.jpg";
		}
		va_chercher_et_affiche_explications(toto_string);
	    }else
		va_chercher_et_affiche_explications(explic);
	}
	void pouvoir_separateur(){
	    y_du_PM=objet[num_obj_pm].pt.y;
	    lambda_min=min2+indice_lamb*(max2-min2)/100.;
	    lambda_max=lambda_min+(max2-min2)/20000.;
	    double inten=0.,difference_chemin_op=0.;
	    obj_perot_lame.y_sur_le_plan_amont=objet[num_obj_laser].pt.y;
	    if(interferon==lame_int)
		coco=lame_semi.carac2;
	    else if(interferon==lame_par_reflex)
		coco=0.707;//cosi=0.707=sini; nsini/n=sini
	    else if(interferon==fbr_prt)
		coco=1.;
	    difference_chemin_op=2.*obj_perot_lame.carac*coco;
	    difference_chemin_op*=cms_par_pixels;
	    lambdaq=lambda;
	    for(int p_lamb=0;p_lamb<300;p_lamb++){
		lambda=lambda_min+(p_lamb/300.)*(lambda_max-lambda_min);
		for(int ptt_y=-149;ptt_y<150;ptt_y++)
		    chgt_nb_lambda[ptt_y+150]=false;
		determiner_ii_z_min_max(true);
		amplitude_source_ponctuelle[150][150].assigne(0.,0.);
		fab_lame.calcul_des_chemins_transmission(0,true);
		fab_lame.calcul_des_chemins_transmission(0,false);
		inten=amplitude_source_ponctuelle[150][150].longueur_carre();
		sauve_les_intensites(p_lamb,sp_couleur.intensite_couleur,0,inten,0);
		determiner_ii_z_min_max(false);
		if(p_lamb/10*10==p_lamb)
		    System.out.println("p_lamb "+p_lamb+" inten "+(float)inten+" intensite_norme  "+(float)intensite_norme);
	    }
	    lambda=lambdaq;
	    //System.out.println("intensite_norme  "+(float)intensite_norme);
	}
	void gere_plot_intensites_1dim(int ptt_y){
	    if(ptt_y<-145){
		if(!lame_reflexion){
		    x0_dessin=100-1;
		    if((perot_fabryyyy||lame_transmission)&&lentle_2!=null&&!source_etendue&&!lumiere_composee)
			fact0_dessin=-40;
		    else
			fact0_dessin=-90;
		}else{
		    x0_dessin=0;
		    fact0_dessin=90;
		}
		//System.out.println(" xxxxxxx x0_dessin "+x0_dessin+" fact0_dessin "+fact0_dessin); 
	    }
	    toto.assigne(x0_dessin,ypoint[150+ptt_y]);
	    plot_intensites(toto,x0_dessin,fact0_dessin,ptt_y);
	}
	void calcule_surface_source_etendue(){
	    dpt_y_source_etendue=src.n_sources_y;
	    dpt_z_source_etendue=src.n_sources_z;
	    spectre_source_etendue_fait=true;  
	}
    }
    class interferometre_michelson extends fabry_perot_ou_lame_ou_mich{
	boolean source_au_foyer=false,miroir_gauche_deregle=false,miroir_bas_deregle=false;
	boolean un_point_dans_le_champ=false;
	resultats resu,resusu;point_coeff pc_deb,pc_deb1;
	resultats ergebnis[][]=new resultats[300][300];
	double ch_g=0.,ch_b=0.,difference_chemin_optique=0.,distance_suppl=0.,facte=0.;
	boolean logic_gauche=false,logic_bas=false;
	point distance_centre=new point(0.,0.);double dist2=0.;
	double derivee_y=0.,derivee_z=0;
	double thetaa=0.;
	boolean bas_ou_gauche=true;
	double y_pixels_ecran[]=new double[300];
	point pt_sortie_gauche,pt_precedant_sortie_gauche;
	resultats result;int ptt_deb_y=-149,ptt_deb_z=1;
	//resultats result1[][]=new resultats[300][300];
	final String angle_des_2_miroirs_string[]=new String[5];
	final String angle_miroir_gauche_[]=new String[5];
	final String angle_miroir_bas_[]=new String[5];
	final double angle_deregle[]=new double[5];
	final double angle_des_2_miroirs[]=new double[5];
	final MenuItem angle_des_2_miroirs_item[]=new MenuItem[5];
	final MenuItem angle_miroir_gauche[]=new MenuItem[5];
	final MenuItem angle_miroir_bas[]=new MenuItem[5];
	double chem_gauche=0.,chem_bas=0.;double pointeur_vers_y_de_l_autre=0,pointeur_vers_z_de_l_autre=0.;
	double distance_image_source_a_lentille1=0.;
	point chemin_partiel_gauche[]=new point[20];
	point chemin_partiel_bas[]=new point [20];
	point chemin_partiel_deregle_gauche[]=new point [20];
	point chemin_partiel_deregle_bas[]=new point [20];
	boolean rayon_gauche=true,bi_rayon=true;
	int index_chemin_gauche=0,index_chemin_bas=0;
	interferometre_michelson(){
	    super();
	    michelsonne=true;
	    echelle_transverse=2.;
	    src=new source(150.,y_banc-115.,0.,0,la_source,0.,0.,0.,nb_objets);
	    src.pt.print(" src.pt +y_banc "+y_banc);
	    num_obj_lentille1=nb_objets-1;
	    //epaisseur=0.1;
	    epaisseur=4.;
	    lame_semi=new lame_semireflechissante(150.,y_banc,pi/4.,70,lame_semireflechissante,epaisseur,1.5,0.5,nb_objets);
	    num_obj_lame_semi=nb_objets-1;
	    miroir_gauche=new miroir_plan(50.,y_banc,0.,80,miroir_plan,0.,0.,1.,nb_objets);
	    num_obj_miroir_gauche=nb_objets-1;
	    lame=new lame_a_faces_paralleles(150.-epaisseur*sqrt2,y_banc,pi/4.,70,lame_a_faces_paralleles,epaisseur,1.5,0.,nb_objets);
	    num_obj_lame=nb_objets-1;
	    //miroir_bas=new miroir_plan(150.,y_banc+100.,pi/2.,80,miroir_plan,0.,0.,1.,nb_objets);
	    miroir_bas=new miroir_plan(150.,y_banc+100.1,pi/2.,80,miroir_plan,0.,0.,1.,nb_objets);
	    num_obj_miroir_bas=nb_objets-1;
	    photom=new PM(300.,y_banc,0.,200,le_PM,0.,0.,0.,nb_objets);
	    result=new resultats();resu=new resultats();resusu=new resultats();
	    lame_d_air=true;
	    unite_z=2*angle_limite_initial/300.*(lame_semi.pt0.y-src.pt0.y);	    
	    angle_miroir_gauche_[0]="angle miroir gauche 0.00001rd";
	    angle_miroir_gauche_[1]="angle miroir gauche 0.00002rd";
	    angle_miroir_gauche_[2]="angle miroir gauche 0.00005rd";
	    angle_miroir_gauche_[3]="angle miroir gauche 0.0001rd";
	    angle_miroir_gauche_[4]="angle miroir gauche 0.0002rd";
	    angle_miroir_bas_[0]="angle miroir bas 0.00001rd";
	    angle_miroir_bas_[1]="angle miroir bas 0.00002rd";
	    angle_miroir_bas_[2]="angle miroir bas 0.00005rd";
	    angle_miroir_bas_[3]="angle miroir bas 0.0001rd";
	    angle_miroir_bas_[4]="angle miroir bas 0.0002rd";
	    angle_deregle[0]=0.00001;
	    angle_deregle[1]=0.00002;
	    angle_deregle[2]=0.00005;
	    angle_deregle[3]=0.0001;
	    angle_deregle[4]=0.0002;
	    angle_des_2_miroirs[0]=0.01;
	    angle_des_2_miroirs[1]=0.02;
	    angle_des_2_miroirs[2]=0.05;
	    angle_des_2_miroirs[3]=0.1;
	    angle_des_2_miroirs[4]=0.2;

	    angle_des_2_miroirs_string[0]="angle coin 0.01rd";
	    angle_des_2_miroirs_string[1]="angle coin 0.02rd";
	    angle_des_2_miroirs_string[2]="angle coin 0.05rd";
	    angle_des_2_miroirs_string[3]="angle coin 0.1rd";
	    angle_des_2_miroirs_string[4]="angle coin 0.2rd";

	    for(int ik=0;ik<5;ik++){
		angle_miroir_gauche[ik]=new MenuItem(angle_miroir_gauche_[ik]);
		angle_miroir_bas[ik]=new MenuItem(angle_miroir_bas_[ik]);
		angle_des_2_miroirs_item[ik]=new MenuItem(angle_des_2_miroirs_string[ik]);
	    }
	    for(int ik=0;ik<20;ik++){
		chemin_partiel_gauche[ik]=new point(zer);
		chemin_partiel_bas[ik]=new point(zer);
		chemin_partiel_deregle_gauche[ik]=new point(zer);
		chemin_partiel_deregle_bas[ik]=new point(zer);
	    }
	    pt_sortie_gauche=new point(0.,0.);
	    pt_precedant_sortie_gauche=new point(0.,0.);
	    int iy=0,iz=0;
	    for( iy=0;iy<300;iy++){
		for(iz=0;iz<300;iz++){
		    ergebnis[iy][iz]=new resultats();
		    //result1[iy][iz]=new resultats();
		}		
	    }		
	    pt_y_z_a_matcher=new point_y_z(0.,0.);
	    pc_deb=new point_coeff(0.,0.,0.);
	    pc_deb1=new point_coeff(0.,0.,0.);
	}
	void traite_command(){
	    traite_comm();
	    for (int i=0;i<5;i++)
		if(command==angle_miroir_gauche_[i]){
		    initialise_menu_modifier();
		    if(!lame_mais_miroirs_non_perp)
			miroir_gauche.angle_vs_normale_au_banc=angle_deregle[i];
		    else
			miroir_gauche.angle_vs_normale_au_banc=angle_coin0+angle_deregle[i];
		    miroir_gauche.calculs_face_penchee(miroir_gauche.angle_vs_normale_au_banc,false);
		    //miroir_bas.pt.y=lame_semi.pt.y+(lame_semi.pt.x-miroir_gauche.pt.x);
		    menu_vib.setEnabled(false);
		    michelson_deregle=true;
		    miroir_gauche_deregle=true;
		    //itep_composee.setEnabled(false);
		    itep_miroir_gauche_deregle.setEnabled(true);
		    itep_miroir_bas_deregle.setEnabled(true); 
		    itep_mettre_lentille_finale.setEnabled(false);
		    barre_des_menus();
		    System.out.println("command "+command);
		    System.out.println(" i "+i+" angle_miroir_gauche_[i] "+angle_miroir_gauche_[i]+" du_nouveau_a_voir "+du_nouveau_a_voir+" intensites_de_base_deja_calculees "+intensites_de_base_deja_calculees);
		    //objet[821]=null;
		    break;
		}
	    for (int i=0;i<5;i++)     
		if(command==angle_miroir_bas_[i]){
		    initialise_menu_modifier();
		    if(!lame_mais_miroirs_non_perp)
			miroir_bas.angle_vs_normale_au_banc=angle_deregle[i];
		    else
			miroir_bas.angle_vs_normale_au_banc=angle_coin0+angle_deregle[i];
		    miroir_bas.calculs_face_penchee(miroir_bas.angle_vs_normale_au_banc,true);
		    //miroir_bas.pt.y=lame_semi.pt.y+(lame_semi.pt.x-miroir_gauche.pt.x);
		    menu_vib.setEnabled(false);
		    //itep_composee.setEnabled(false);
		    itep_miroir_gauche_deregle.setEnabled(true);
		    itep_miroir_bas_deregle.setEnabled(true);
		    michelson_deregle=true;
		    itep_mettre_lentille_finale.setEnabled(false);
		    miroir_bas_deregle=true; 
		    barre_des_menus();
		    break;
		}
	    if(command=="Chemins optiques egaux"){
		miroir_bas.pt.y=lame_semi.pt.y+(lame_semi.pt.x-miroir_gauche.pt.x);
		revenir_aux_conditions_de_depart();
		intensites_de_base_deja_calculees=false; 
		matching_et_intensites_deja_calcules=false;
		resultats_f_d_angle_deja_calcules=false;
		command="";
		du_nouveau_a_voir=true;
	    }		
	    for (int i=0;i<5;i++)
		if(command==angle_des_2_miroirs_string[i]){
		    initialise_menu_modifier();
		    miroir_gauche.angle_vs_normale_au_banc=angle_des_2_miroirs[i];
		    miroir_bas.angle_vs_normale_au_banc=angle_des_2_miroirs[i];
		    miroir_gauche.calculs_face_penchee(angle_des_2_miroirs[i],false);
		    miroir_bas.calculs_face_penchee(angle_des_2_miroirs[i],true);
		    miroir_gauche_deregle=false;
		    miroir_bas_deregle=false;
		    src.pt.y=y_banc-115.;
		    angle_coin0=angle_des_2_miroirs[i];
		    menu_vib.setEnabled(true);
		    itep_composee.setEnabled(true);
		    itep_miroir_gauche_deregle.setEnabled(true);
		    itep_miroir_bas_deregle.setEnabled(true);
		    barre_des_menus();
		    lame_mais_miroirs_non_perp=true;
		    break;
		}
	}
	void explique(String explic){
	    System.out.println(" explic "+explic+" explique_lumiere_composee "+explique_lumiere_composee);
	    if(explic=="")
		va_chercher_et_affiche_explications("C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_michelson.jpg");
	    else
		va_chercher_et_affiche_explications(explic);
	}
	void va_chercher_les_coordonnees(int pt_y,int pt_z){
	    if(!bas_ou_gauche){
		y_ppmm=(int)((ergebnis[150+pt_y][pt_z].bas.y-y_banc)*echelle_transverse+plaque_photo.centre.y);
		z_ppmm=(int)(ergebnis[150+pt_y][pt_z].bas.z*echelle_transverse+plaque_photo.centre.x);
	    }else{
		y_ppmm=(int)((ergebnis[150+pt_y][pt_z].gauche.y-y_banc)*echelle_transverse+plaque_photo.centre.y);
		z_ppmm=(int)(ergebnis[150+pt_y][pt_z].gauche.z*echelle_transverse+plaque_photo.centre.x);//grandissement 2
	    }
	}
	void gere_plot_intensites_1dim(int ptt_y){
	    if(!bas_ou_gauche){
		photom.pt.y=(int)((ergebnis[150+ptt_y][150].bas.y-y_banc)*echelle_transverse+y_banc);
	    }else
		photom.pt.y=(int)((ergebnis[150+ptt_y][150].gauche.y-y_banc)*echelle_transverse+y_banc);
	    x0_dessin=0;
	    fact0_dessin=70;
	    plot_intensites(photom.pt,x0_dessin,fact0_dessin,ptt_y);

	}
	void calcule_surface_source_etendue(){
	    int lim_y=0,lim_z=150;
	    double delta_y=1.,delta_z=1.;
	    pt_y_z_a_matcher.y=position_centrale(0,150,true)-src.n_sources_y*delta_y;
	    ptt_deb_y=0;
	    pt_y_z_a_matcher.z=position_centrale(0,150,false)+src.n_sources_z*delta_z;
	    ptt_deb_z=150;
	    System.out.println("");
	    pt_y_z_a_matcher.print("ptt_deb_y"+ptt_deb_y+"ptt_deb_z"+ptt_deb_z+"pt_y_z_a_matcher");
	    tata_y_z.assigne(matcher_les_rayons_source_etendue(pt_y_z_a_matcher));
	    dpt_y_source_etendue=(int)Math.round(tata_y_z.y-lim_y);
	    dpt_z_source_etendue=(int)Math.round(tata_y_z.z-lim_z);
	    //src.n_sources_y=dpt_y_source_etendue;
	    //src.n_sources_z=dpt_z_source_etendue;
	    tata_y_z.print(" tata_y_z");
	    spectre_source_etendue_fait=true;  
	}	
	void incremente_chemins_partiels(double cici){
	    if(bi_rayon||rayon_gauche){
		if(!michelson_deregle){
		    chemin_partiel_gauche[index_chemin_gauche].x=cici;
		    if(index_chemin_gauche!=0)
			chemin_partiel_gauche[index_chemin_gauche].y=chemin_partiel_gauche[index_chemin_gauche-1].y+cici;
		    else
			chemin_partiel_gauche[index_chemin_gauche].y=cici;
		}else{
		    chemin_partiel_deregle_gauche[index_chemin_gauche].x=cici;
		    if(index_chemin_gauche!=0)
			chemin_partiel_deregle_gauche[index_chemin_gauche].y=chemin_partiel_deregle_gauche[index_chemin_gauche-1].y+cici;
		    else
			chemin_partiel_deregle_gauche[index_chemin_gauche].y=cici;
		}
		index_chemin_gauche++;
	    }
	    if(bi_rayon||!rayon_gauche){
		if(!michelson_deregle){
		    chemin_partiel_bas[index_chemin_bas].x=cici;
		    if(index_chemin_bas!=0)
			chemin_partiel_bas[index_chemin_bas].y=chemin_partiel_bas[index_chemin_bas-1].y+cici;
		    else
			chemin_partiel_bas[index_chemin_bas].y=cici;
		}else{
		    chemin_partiel_deregle_bas[index_chemin_bas].x=cici;
		    if(index_chemin_bas!=0)
			chemin_partiel_deregle_bas[index_chemin_bas].y=chemin_partiel_deregle_bas[index_chemin_bas-1].y+cici;
		    else
			chemin_partiel_deregle_bas[index_chemin_bas].y=cici;
		}
		index_chemin_bas++;
	    }
	}
	void remise_a_zero_chemins_partiels(){
		if(!michelson_deregle){
		    for(int ik=0;ik<20;ik++){
			chemin_partiel_gauche[ik].assigne(zer);
			chemin_partiel_bas[ik].assigne(zer);
		    }
		}else{
		    for(int ik=0;ik<20;ik++){
			chemin_partiel_deregle_gauche[ik].assigne(zer);
			chemin_partiel_deregle_bas[ik].assigne(zer);
		    }
		}
		index_chemin_gauche=0;index_chemin_bas=0;
		
	}
	void imprime_chemins_partiels(){
	    for(int ik=0;ik<9;ik++)
		chemin_partiel_gauche[ik].print(" ik "+ik+" chemin_partiel_gauche[ik] ");
	    for(int ik=0;ik<9;ik++)
		chemin_partiel_deregle_gauche[ik].print(" ik "+ik+" chemin_partiel_deregle_gauche[ik] ");
	    for(int ik=0;ik<9;ik++)
		chemin_partiel_bas[ik].print(" ik "+ik+" chemin_partiel_bas[ik] ");
	    for(int ik=0;ik<9;ik++)
		chemin_partiel_deregle_bas[ik].print(" ik "+ik+" chemin_partiel_deregle_bas[ik] ");
	    toto.assigne_soustrait(chemin_partiel_deregle_gauche[8],chemin_partiel_bas[8]);
	    toto.print(" diff chemin final toto ");	    
	}

	void revenir_aux_conditions_de_depart(){
	    //lame_d_air=true;
	    michelson_deregle=false;
	    miroir_gauche_deregle=false;
	    miroir_bas_deregle=false;
	    lame_mais_miroirs_non_perp=false;
	    miroir_gauche.angle_vs_normale_au_banc=0.;
	    miroir_bas.angle_vs_normale_au_banc=0.;
	    miroir_gauche.calculs_face_penchee(miroir_gauche.angle_vs_normale_au_banc,false);
	    miroir_bas.calculs_face_penchee(miroir_gauche.angle_vs_normale_au_banc,true);
	}
	void pouvoir_separateur(){
	    diff_chem=ergebnis[150][150].gauche.chemin-ergebnis[150][150].bas.chemin;
	    lambda_min=min2+indice_lamb*(max2-min2)/100.;
	    lambda_max=lambda_min+(max2-min2)/50.;
	    lambdaq=lambda;
	    for(int p_lamb=0;p_lamb<300;p_lamb++){
		lambda=lambda_min+(p_lamb/300.)*(lambda_max-lambda_min);
		difference_phase_michelson=diff_chem*2*pi/lambda;
		intensite=1.+Math.cos(difference_phase_michelson);
		sauve_les_intensites(p_lamb,sp_couleur.intensite_couleur,0,intensite,0);
	    }
	    lambda=lambdaq;
	}
	double reperage_y(int ptz,double y_match,int un_ou_deux,resultats[][] res){
	    int ptt=0;
	    for(ptt=ptt_deb_y;ptt<150;ptt++){
		if(un_ou_deux==2){
		    y1_rayons_differents=res[150+ptt][ptz].bas.y;
		    y1_rayons_differentsm1=res[150+ptt-1][ptz].bas.y;
		}else if(un_ou_deux==1){
		    y1_rayons_differents=res[150+ptt][ptz].gauche.y;
		    y1_rayons_differentsm1=res[150+ptt-1][ptz].gauche.y;
		}
		if(y1_rayons_differents>y1_rayons_differentsm1)
		    logic=y1_rayons_differents-y_match>0.&&y1_rayons_differentsm1-y_match<=0.;
		else
		    logic=y1_rayons_differents-y_match<0.&&y1_rayons_differentsm1-y_match>=0.;
		/*
		if(toto_bool_autre_am)		
		    System.out.println("ptt "+ptt+" un_ou_deux "+un_ou_deux+" y_match "+(float)y_match+" y1_rayons_differents "+(float)y1_rayons_differents+" logic "+logic);
		*/
		if(logic){
		    coco=Math.abs((y_match-y1_rayons_differents)/(y1_rayons_differents-y1_rayons_differentsm1));
		    //if(!source_etendue)
		    //if(!michelson_deregle)
			ptt_deb_y=ptt-8;
			//else
			//ptt_deb_y=ptt-10;
		    if(ptt_deb_y<-149)
			ptt_deb_y=-149;
		    //if(i_demarre!=-1&&un_ou_deux==1)
		    //System.out.println("ptt "+ptt+"ptt_deb_y "+ptt_deb_y+" coco "+(float)coco);
		    cucu=ptt-coco;
		    if(cucu<0)
			cucu-=0.00000000001;
		    return (cucu);
		}
	    }
	    /*
	    if(source_etendue){
		System.out.println("ptt "+ptt+"ptt_deb_y "+ptt_deb_y);
		objet[206]=null;
	    }
	    */
	    return (-1000.);
	}
	double reperage_z(double z_match,int un_ou_deux,resultats[][] res,double pointeur_vers_y){
	    for(int ptt=ptt_deb_z;ptt<300;ptt++){
		if(un_ou_deux==2){
		    z1_rayons_differents=res[150+(int)Math.round(pointeur_vers_y)][ptt].bas.z;
		    z1_rayons_differentsm1=res[150+(int)Math.round(pointeur_vers_y)][ptt-1].bas.z;
		}else if(un_ou_deux==1){
		    z1_rayons_differents=res[150+(int)Math.round(pointeur_vers_y)][ptt].gauche.z;
		    z1_rayons_differentsm1=res[150+(int)Math.round(pointeur_vers_y)][ptt-1].gauche.z;
		}
		/*
		if(un_ou_deux==2){
		    z1_rayons_differents=res[150+(int)Math.round(ptt_y)][ptt].bas.z;
		    z1_rayons_differentsm1=res[150+(int)Math.round(ptt_y)][ptt-1].bas.z;
		}else if(un_ou_deux==1){
		    z1_rayons_differents=res[150+(int)Math.round(ptt_y)][ptt].gauche.z;
		    z1_rayons_differentsm1=res[150+(int)Math.round(ptt_y)][ptt-1].gauche.z;
		}
		*/
		if(z1_rayons_differents>z1_rayons_differentsm1)
		    logic=z1_rayons_differents-z_match>0.&&z1_rayons_differentsm1-z_match<=0.;
		else
		    logic=z1_rayons_differents-z_match<0.&&z1_rayons_differentsm1-z_match>=0.;
		//if(michelson_deregle&&i_demarre!=1&&ptt/10*10==ptt)		
		//   System.out.println("ptt "+ptt+"ptt_z "+ptt_z+" z_match "+(float)z_match+" z1_rayons_differents "+(float)z1_rayons_differents);
		if(logic){
		    coco=Math.abs((z_match-z1_rayons_differents)/(z1_rayons_differents-z1_rayons_differentsm1));
		    //if(!source_etendue)
		    //if(!michelson_deregle)
			ptt_deb_z=ptt-8;
		    //else
		    //ptt_deb_z=ptt-20;
		    if(ptt_deb_z<1)
			ptt_deb_z=1;
		    return (ptt-coco);
		}
	    }
	    return (-1000.);
	}
	triple_double interpollation(double p_y,double p_z,resultats[][] res,boolean gauche){
	    if(p_y<0){
		toto_int_y=(int)(-p_y);
		dodo=1.-(-p_y-toto_int_y);		
		toto_int_y=(int)(p_y+150);
	    }else{
		toto_int_y=(int)(p_y);
		dodo=p_y-toto_int_y;
		toto_int_y+=150;
	    }
	    if(dodo>0.5&&toto_int_y<298){
		toto_int_y+=1;
		dodo=-(1.-dodo);
	    }
	    aoao=p_z-(int)p_z;
	    toto_int_z=(int)p_z;
	    if(aoao>0.5&&toto_int_z<298){
		toto_int_z+=1;
		aoao=-(1.-aoao);
	    }
	    if(toto_int_z>298)
		toto_int_z=298;
	    if(toto_int_y>298)
		toto_int_y=298;
	    
	    if(gauche){
		derivee_y=(res[toto_int_y+1][toto_int_z].gauche.chemin-res[toto_int_y][toto_int_z].gauche.chemin);
		derivee_z=(res[toto_int_y][toto_int_z+1].gauche.chemin-res[toto_int_y][toto_int_z].gauche.chemin);
		tata_trd.r=res[toto_int_y][toto_int_z].gauche.chemin+derivee_y*dodo+derivee_z*aoao;
		derivee_y=(res[toto_int_y+1][toto_int_z].gauche.y-res[toto_int_y][toto_int_z].gauche.y);
		tata_trd.v=res[toto_int_y][toto_int_z].gauche.y+derivee_y*dodo;
		//tata_trd.b=res[toto_int_y][toto_int_z].gauche.z+derivee_z*aoao;
		derivee_z=(res[toto_int_y][toto_int_z+1].gauche.z-res[toto_int_y][toto_int_z].gauche.z);
		tata_trd.b=res[toto_int_y][toto_int_z].gauche.z+derivee_z*aoao;
		if(Math.abs(p_z-100.)<0.1&&Math.abs(toto_int_y)==50){
		    tata_trd.print("toto_int_y "+toto_int_y+" p_z "+p_z+" derivee_z "+(float)derivee_z+" aoao "+(float)aoao+" toto_int_z "+toto_int_z+" res[toto_int_y][toto_int_z].gauche.y "+(float)res[toto_int_y][toto_int_z].gauche.y+" tata_trd ");
		    System.out.println(" res[toto_int_y][toto_int_z].gauche.z "+(float)res[toto_int_y][toto_int_z].gauche.z+" derivee_z "+(float)derivee_z);
		}
	    }else{
		derivee_y=(res[toto_int_y+1][toto_int_z].bas.chemin-res[toto_int_y][toto_int_z].bas.chemin);
		derivee_z=(res[toto_int_y][toto_int_z+1].bas.chemin-res[toto_int_y][toto_int_z].bas.chemin);
		tata_trd.r=res[toto_int_y][toto_int_z].bas.chemin+derivee_y*dodo+derivee_z*aoao;
		derivee_y=(res[toto_int_y+1][toto_int_z].bas.y-res[toto_int_y][toto_int_z].bas.y);
		tata_trd.v=res[toto_int_y][toto_int_z].bas.y+derivee_y*dodo;
		//tata_trd.b=res[toto_int_y][toto_int_z].bas.z+derivee_z*aoao;
		derivee_z=(res[toto_int_y][toto_int_z+1].bas.z-res[toto_int_y][toto_int_z].bas.z);
		tata_trd.b=res[toto_int_y][toto_int_z].bas.z+derivee_z*aoao;
	    }
	    return tata_trd;
	}
	triple_double autre_amplitude_rayons_differents(int pt_z,point_y_z y_z,int un_ou_deux,resultats[][] res){
	    //if(Math.abs(ptt_y)<6)
	    //System.out.println("ptt_y "+ptt_y+" y_a_matcher "+y_a_matcher);
	    y_a_matcher=y_z.y;
	    if(i_demarre!=-1){
		pointeur_vers_y_de_l_autre=reperage_y(pt_z,y_a_matcher,un_ou_deux,res);
	    }else{
		pointeur_vers_y_de_l_autre=reperage_y(pt_z,y_a_matcher,un_ou_deux,res);
		if(pointeur_vers_y_de_l_autre>-500){
		    coeff_initial=-angle_limite_initial+2*angle_limite_initial/300*(pointeur_vers_y_de_l_autre+150);
		    pc_trouve_b.assigne(src.pt,coeff_initial);
		    trajets(pc_trouve_b,(int)Math.round(pointeur_vers_y_de_l_autre+150),true,true);// ce sera sauve dans result
		}
	    }
	    if(pointeur_vers_y_de_l_autre<-500){
		toto_trd.assigne(-1000.,-1000.,-1000.);
		return toto_trd;
	    }
	    z_a_matcher=y_z.z;
	    if(i_demarre==-1){
		pointeur_vers_z_de_l_autre=150.;
	    }else{
		pointeur_vers_z_de_l_autre=reperage_z(z_a_matcher,un_ou_deux,res,pointeur_vers_y_de_l_autre);
		//if(ptt_z==100&&Math.abs(ptt_y)==50)
		//    System.out.println(" z_a_matcher "+(float)z_a_matcher+" pointeur_vers_z_de_l_autre "+pointeur_vers_z_de_l_autre+" pointeur_vers_y_de_l_autre "+pointeur_vers_y_de_l_autre);
		if(pointeur_vers_z_de_l_autre<-500){
		    toto_trd.assigne(-1000.,-1000.,-1000.);
		    return toto_trd;
		}
	    }
	    if(!michelson_deregle||miroir_gauche_deregle)
		result.gauche.assigne(interpollation(pointeur_vers_y_de_l_autre,pointeur_vers_z_de_l_autre,res,true));
	    if(!michelson_deregle||miroir_bas_deregle)
		result.bas.assigne(interpollation(pointeur_vers_y_de_l_autre,pointeur_vers_z_de_l_autre,res,false));
	    if(un_ou_deux==2){
		toto_trd.r=result.bas.chemin;
		toto_trd.v=result.bas.y;
		toto_trd.b=result.bas.z;
		//if(ptt_z==150&&Math.abs(ptt_y)==50)
		//   toto_trd.print("ptt_y "+ptt_y+"ptt_z "+ptt_z+"vvv pointeur_vers_y_de_l_autre "+(float)pointeur_vers_y_de_l_autre+"chemin_optiquepix*cms_par_pixels "+(float)chemin_optique*cms_par_pixels+"toto_trd (bas) ");
		return toto_trd;
	    }else{
		toto_trd.r=result.gauche.chemin;
		toto_trd.v=result.gauche.y;
		toto_trd.b=result.gauche.z;
		return toto_trd;
	    }
	}
	void trajets(point_coeff pca,int po,boolean sauver_dans_result,boolean sauver){
	    bi_rayon=true;
	    pc_deb.assigne(pca);
	    chemin_optique=0.;
	    if(po!=0)
		pc_deb.coeff=1/pc_deb.coeff;
	    else
		pc_deb.coeff=1.e12;
	    pc_deb1.pt.assigne(intersection_avec_plan(lame.pt,lame.coefff,pc_deb));
	    if(i_demarre!=-1){
		j_lim_bas=0;j_lim_haut=300;
	    }else{
		j_lim_bas=150;j_lim_haut=151;
	    }
	    pt_B.assigne_soustrait(pc_deb1.pt,pca.pt);
	    if(Math.abs(pt_B.x)<1.e-12)
		pt_B.x=1.e-12;  
	    for(int j=j_lim_bas;j<j_lim_haut;j++){
		chemin_optique_avec_z=0.;
		ou_j_en_suis=" premiere intersection avec lame";
		//coco=lame_semi.pt.y-(src.pt.x-lame_semi.pt.x);
		//pc_zz=0.09*(j-150)*(coco-src.pt.y)/(coco-src.pt0.y);//c'est donc le z à la face d'entree de la première lame
		pc_zz=(j-150)*unite_z;
		//if(lentle_2!=null)
		//pc_zz*=0.7;
		//if(src.pt.x>src.pt0.x)
		//    pc_zz/=((src.pt.x-src.pt0.x+5)/20);
		pc_z_prec=pc_zz;
		c_dir.z=0;c_dir.pt.x=1.;//pour demarrer avec le bon z dans drawlinf
		drawlinf_somme_z(pc_deb1.pt,pc_deb.pt,c_dir,1.,j,po);
		pc_garde.assigne(pc_deb1);
		pzz_initial=pc_zz;
		//chemin_optique_garde=chemin_optique_avec_z;
		coco=Math.sqrt(pt_B.longueur_carre()+pc_zz*pc_zz);
		c_dir.assigne(pt_B.x/coco,pt_B.y/coco,pc_zz/coco);
		c_dir_init.assigne(c_dir);
		c_dir.pt.rotation_x(lame_semi.u_normale_face_penchee,false);
		c_dir.apres_diffraction_rel_x(lame_semi.indice);
		c_dir.pt.rotation_x(lame_semi.u_normale_face_penchee,true);
		pc_deb1.coeff=c_dir.y_sur_x();
		c_dir_second.assigne(c_dir);
		bi_rayon=false;
		//rayon gauche
		if(!michelson_deregle||miroir_gauche_deregle||dessine_parcours){

		    ou_j_en_suis=" traversee de la lame";
		    rayon_gauche=true;
		    toto.assigne(lame.pt);toto.x+=lame.carac*sqrt2;
		    pt_lame.assigne(intersection_avec_plan(toto,lame_semi.coefff,pc_deb1));
		    tata.assigne_additionne(pt_B,pca.pt);
		    drawlinf_somme_z(pt_lame,pc_deb1.pt,c_dir,lame_semi.indice,j,po);
		    ou_j_en_suis=" re traversee de la lame vers la gauche";
		    toto.assigne_additionne(pt_B,pca.pt);toto.soustrait(pt_lame);
		    pt_lame_s.x=pt_lame.x+toto.y;	
		    pt_lame_s.y=pt_lame.y+toto.x;
		    coco=c_dir.pt.x;
		    c_dir.pt.x=-c_dir.pt.y;
		    c_dir.pt.y=coco;
		    drawlinf_somme_z(pt_lame_s,pt_lame,c_dir,objet[num_obj_lame].indice,j,po);
		    ou_j_en_suis=" intersection avec miroir g";
		    c_dir.pt.x=-c_dir_init.pt.y;
		    c_dir.pt.y=-c_dir_init.pt.x;
		    c_dir.z=c_dir_init.z;
		    if(!(lame_mais_miroirs_non_perp||miroir_gauche_deregle)){
			pt_lame_miroir.x=miroir_gauche.pt.x;
			pt_lame_miroir.y=pt_lame_s.y-Math.abs(pt_lame_s.x-miroir_gauche.pt.x)*c_dir.y_sur_x();
		    }else{
			pc_util6.pt.assigne(pt_lame_s);			    
			pc_util6.coeff=c_dir.y_sur_x();
			pt_lame_miroir.assigne(intersection_avec_plan(miroir_gauche.pt,miroir_gauche.coefff,pc_util6));
		    }
		    toto.assigne_soustrait(pt_lame_miroir,pt_lame_s);
		    drawlinf_somme_z(pt_lame_miroir,pt_lame_s,c_dir,1.,j,po);
		    ou_j_en_suis=" retour miroir gauche et intersection lame g";
		    if(lame_mais_miroirs_non_perp||michelson_deregle&&miroir_gauche_deregle)
			c_dir.pt.symetrique_retour(miroir_gauche.u_normale_face_penchee);
		    else
			c_dir.pt.x=-c_dir.pt.x;
		    c_dir_garde.assigne(c_dir);
		    toto_pt_coeff.assigne(pt_lame_miroir,c_dir.y_sur_x());
		    pt_lame.assigne(intersection_avec_plan(lame.pt,lame.coefff,toto_pt_coeff));
		    drawlinf_somme_z(pt_lame,pt_lame_miroir,c_dir,1.,j,po);
		    c_dir.pt.rotation_x(lame.u_normale_face_penchee,false);
		    c_dir.apres_diffraction_rel_x(lame.indice);
		    c_dir.pt.rotation_x(lame.u_normale_face_penchee,true);
		    coeff_avant=pc_util.coeff;
		    ou_j_en_suis=" traversee de la double lame vers la droite ";
		    toto_pt_coeff.assigne(pt_lame,c_dir.y_sur_x());
		    toto.assigne(lame.pt);
		    toto.x+=(lame.carac+lame_semi.carac)*sqrt2;
		    pt_lame_s.assigne(intersection_avec_plan(toto,objet[num_obj_lame].coefff,toto_pt_coeff));
		    drawlinf_somme_z(pt_lame_s,pt_lame,c_dir,lame.indice,j,po);
		    c_dir.assigne(c_dir_garde);
		    ou_j_en_suis=" sur l'ecran par la gauche ";
		    pt_y_z_final_gauche.assigne(sortie_z_bis(j,po,rayon_gauche,pt_lame_s));
		    //if(miroir_gauche_deregle&&j==1&&po/10*10==po)
		    //pt_y_z_final_gauche.print("po "+po+" j "+j+" pt_y_z_final_gauche ");
		    if(sauver)
			if(sauver_dans_result){
			    result.gauche.chemin=chemin_optique*cms_par_pixels;
			    result.gauche.y=pc_exit.pt.y;
			}else{
			    ergebnis[150+po][j].gauche.y=pt_y_z_final_gauche.y;
			    ergebnis[150+po][j].gauche.z=pt_y_z_final_gauche.z;
			    ergebnis[150+po][j].gauche.chemin=chemin_optique_avec_z*cms_par_pixels;//1pixel=0.4cm
			}
		}
		if(!michelson_deregle||miroir_bas_deregle||dessine_parcours){
		    //rayon du bas
		    rayon_gauche=false;
		    coeff_avant=pc_garde.coeff;
		    pc_util.assigne(pc_garde);
		    ou_j_en_suis=" premiere intersection de la lame bas";
		    chemin_optique_avec_z=0.;
		    pc_zz=pzz_initial;
		    pc_z_prec=pzz_initial;
		    if(dessine_parcours&&j==0)
			if(!vibration)
			    System.out.println(" chemin_optique_avec_z "+(float)chemin_optique_avec_z+" pc_zz "+pc_zz); 
		    c_dir.z=0;c_dir.pt.x=1.;//pour demarrer avec le bon z dans drawlinf
		    drawlinf_somme_z(pc_deb1.pt,pc_deb.pt,c_dir,1.,j,po);
		    ou_j_en_suis=" premier traversee de la double lame bas";
		    c_dir.assigne(c_dir_second);
		    toto.assigne(lame.pt);
		    toto.x+=(lame.carac+lame_semi.carac)*sqrt2;
		    toto_pt_coeff.assigne(pc_garde.pt,c_dir.y_sur_x());
		    pt_lame.assigne(intersection_avec_plan(toto,lame.coefff,toto_pt_coeff));
		    tata.assigne_additionne(pt_B,pca.pt);
		    drawlinf_somme_z(pt_lame,tata,c_dir,objet[num_obj_lame].indice,j,po);
		    c_dir.assigne(c_dir_init);
		    ou_j_en_suis=" intersection avec miroir b";
		    if(!(lame_mais_miroirs_non_perp||miroir_bas_deregle)){
			pt_lame_miroir.y=miroir_bas.pt.y;
			pt_lame_miroir.x=pt_lame.x-(pt_lame.y-miroir_bas.pt.y)*c_dir.x_sur_y();
		    }else{
			pc_util6.pt.assigne(pt_lame);
			pc_util6.coeff=c_dir.y_sur_x();
			pt_lame_miroir.assigne(intersection_avec_plan(miroir_bas.pt,miroir_bas.coefff,pc_util6));
		    }
		    drawlinf_somme_z(pt_lame_miroir,pt_lame,c_dir,1.,j,po);
		    ou_j_en_suis=" retour miroir bas et intersection lame ";
		    if(!(lame_mais_miroirs_non_perp||michelson_deregle&&miroir_bas_deregle))
			c_dir.pt.y=-c_dir.pt.y;
		    else
			c_dir.pt.symetrique_retour(miroir_bas.u_normale_face_penchee);
		    c_dir_garde.assigne(c_dir);
		    toto.assigne(lame_semi.pt);toto.x+=lame_semi.carac*sqrt2;
		    toto_pt_coeff.assigne(pt_lame_miroir,c_dir.y_sur_x());
		    pt_lame.assigne(intersection_avec_plan(toto,lame_semi.coefff,toto_pt_coeff));
		    drawlinf_somme_z(pt_lame,pt_lame_miroir,c_dir,1.,j,po);
		    ou_j_en_suis=" passage vers le haut dans la lame_semi,b ";
		    toto.assigne_facteur(lame_semi.u_normale_face_penchee,-1.);
		    c_dir.pt.rotation_x(toto,false);
		    c_dir.apres_diffraction_rel_x(lame_semi.indice);
		    c_dir.pt.rotation_x(toto,true);
		    toto_pt_coeff.assigne(pt_lame,c_dir.y_sur_x());
		    pt_lame_s.assigne(intersection_avec_plan(lame_semi.pt,lame_semi.coefff,toto_pt_coeff));
		    drawlinf_somme_z(pt_lame_s,pt_lame,c_dir,lame_semi.indice,j,po);
		    ou_j_en_suis=" passage vers la droite dans la lame_semi b ";
		    toto.assigne_soustrait(pt_lame,pt_lame_s);
		    pt_lame.x=pt_lame_s.x+toto.y;	
		    pt_lame.y=pt_lame_s.y+toto.x;
		    coco=c_dir.pt.x;
		    c_dir.pt.x=-c_dir.pt.y;
		    c_dir.pt.y=coco;
		    drawlinf_somme_z(pt_lame,pt_lame_s,c_dir,lame_semi.indice,j,po);
		    c_dir.pt.x=-c_dir_garde.pt.y;
		    c_dir.pt.y=-c_dir_garde.pt.x;
		    c_dir.z=c_dir_garde.z;
		    if(lentle_2!=null&&po==0&&j==100){
			c_dir.print(" j " +j+" c_dir final bas");
			pt_lame.print(" pt_lame ");
		    }
		    ou_j_en_suis=" sur l'ecran par le bas ";
		    pc_util1.coeff=1./coeff_avant;
		    pt_y_z_final_bas.assigne(sortie_z_bis(j,po,rayon_gauche,pt_lame));
		    if(lentle_2==null&&po==0&&j==100){
			pt_y_z_final_bas.print("po "+po+" j"+j+" pt_y_z_final_bas");
			//objet[209]=null;
		    }
		    if(sauver)
			if(sauver_dans_result){
			    result.bas.chemin=chemin_optique*cms_par_pixels;
			    result.bas.y=pc_exit.pt.y;
			}else{
			    ergebnis[150+po][j].bas.y=pt_y_z_final_bas.y;
			    ergebnis[150+po][j].bas.z=pt_y_z_final_bas.z;
			    ergebnis[150+po][j].bas.chemin=chemin_optique_avec_z*cms_par_pixels;//1pixel=0.4cm
			}
		}
	    }	    
	}
	void dessin_agrandi(point p_fina,point p_ini,boolean gch){
	    if(gch){
		pt_sortie_gauche.assigne(p_fina);
		pt_precedant_sortie_gauche.assigne(p_ini);
	    }else{
		toto.assigne_soustrait(p_fina,p_ini);
		toto.multiplie_cst(40./toto.longueur());
		titi.assigne_soustrait(p_fina,toto);
		tata.assigne(p_fina);
		
		toto.assigne_soustrait(pt_sortie_gauche,pt_precedant_sortie_gauche);
		toto.multiplie_cst(40./toto.longueur());
		tutu.assigne_soustrait(pt_sortie_gauche,toto);
		tete.assigne(pt_sortie_gauche);
		
		cucu=(pt_sortie_gauche.y-p_fina.y)*100;
		//cucu=10.;
		titi.y-=cucu;
		tata.y-=cucu;
		drawlinh(titi,tata,Color.orange);
		tutu.y+=cucu;
		tete.y+=cucu;
		drawlinh(tutu,tete,Color.orange);
	    }
	}
	point intersection_avec_lame(point pobj,point_coeff pc,double car){
	    if(Math.abs(pc.coeff)<1.){
		x_intersec=(pobj.x+car+pobj.y-pc.pt.y+pc.pt.x*pc.coeff)/(1.+pc.coeff);
		y_intersec=pobj.y-(x_intersec-(pobj.x+car));
	    }else{
		coco=1./pc.coeff;
		y_intersec=(pobj.y+car+pobj.x-pc.pt.x+pc.pt.y*coco)/(1.+coco);
		x_intersec=pobj.x-(y_intersec-(pobj.y+car));
	    }
	    p_intersec.assigne(x_intersec,y_intersec);
	    return p_intersec;
	}
	void calcul_des_chemins(int ptt_y,double coeff_initial){
	    if(ptt_y==101)
		System.out.println(" entree calcul_des_chemins ");
	    dessine_parcours=(ptt_y==140||ptt_y==-140);
	    if(seulement_pour_montrer_rayons)
		if(!dessine_parcours)
		    return;
		else{
		    if(!vibration)
			System.out.println("okok ");
		}
	    if(dessine_parcours){
		if(!vibration)
		    System.out.println(" kkkkkkkkkptt_y "+ptt_y+" coeff_initial "+(float)coeff_initial);
		toto.assigne(0.,y_banc);
		titi.assigne(800.,y_banc);
		drawlinh(titi,toto,Color.black);
		for(int iq=1;iq<nb_objets;iq++)
		    objet[iq].dessine();
	    }
	    pc_trouve_b.assigne(src.pt,coeff_initial);
	    trajets(pc_trouve_b,ptt_y,false,!seulement_pour_montrer_rayons);
	}
	void intensites_de_base(){
	    if(!vibration)
		System.out.println(" rrrr entree intensites_de_base_michelson ");
	    if(!resultats_f_d_angle_deja_calcules){
		System.out.println(" vers calcul des amplitudes matching_et_intensites_deja_calcules"+matching_et_intensites_deja_calcules);
		unite_z=2*angle_limite_initial/300.*(lame_semi.pt0.y-src.pt0.y);
		System.out.println(" unite_z "+unite_z);
		//if(fin_deplacer_un_objet)
		//objet[238]=null;
		for(int ptt_y=-150;ptt_y<150;ptt_y++){
		    chgt_nb_lambda[ptt_y+150]=false;
		    dchemin_en_lambda_prec=-10000;dchemin_en_lambda=-10000;
		    coeff_initial=-angle_limite_initial+2*angle_limite_initial/300.*(ptt_y+150);
		    calcul_des_chemins(ptt_y,coeff_initial);
		}
		resultats_f_d_angle_deja_calcules=true;
	    }
	    if(!matching_et_intensites_deja_calcules){
		if(lentle_2==null)
		    echelle_transverse=2.;
		else
		    echelle_transverse=20.;
		    /*
		    if(michelson_deregle&&ptt_y/10*10==ptt_y){
			ergebnis[150+ptt_y][1].gauche.print(" ptt_y "+ptt_y+"ergebnis[150+ptt_y][1].gauche ");
			ergebnis[150+ptt_y][1].bas.print(" ptt_y "+ptt_y+"ergebnis[150+ptt_y][1].bas ");
		    }
		    */
		ptt_deb_y=-149;
		int ptt_y=0,ptt_z=0;
		intensite_norme=0.0;
		intensite_max=0.0; 
		System.out.println(" vers calcul des intensites");
		for(ptt_y=-149;ptt_y<150;ptt_y++)
		    if(i_demarre==-1||vibration){
			if(lentle_2==null||lentle_2!=null&&michelson_deregle)
			    resusu.assigne(matcher_les_rayons_sur_l_ecran(ptt_y,150));
			else
			    resusu.assigne(ergebnis[150+ptt_y][150]);
			calcule_et_sauve_intens(ptt_y,150,resusu);
		    }else{
			ptt_deb_z=1;
			for(ptt_z=ii_zmin+1;ptt_z<ii_zmax;ptt_z++){
			    if(lentle_2==null||lentle_2!=null&&michelson_deregle)
				resusu.assigne(matcher_les_rayons_sur_l_ecran(ptt_y,ptt_z));
			    else
				resusu.assigne(ergebnis[150+ptt_y][ptt_z]);
			    calcule_et_sauve_intens(ptt_y,ptt_z,resusu);
			    if(lentle_2!=null&&ptt_y==0&&ptt_z==100){
				resusu.bas.print("ptt_y "+ptt_y+"ptt_z "+ptt_z+" resusu.bas "); 
				//objet[209]=null;
			    }
			}
		    }	
		matching_et_intensites_deja_calcules=true;
	    }
	}
	double position_centrale(int p_y,int p_z,boolean yyy){
	    if(yyy)
		if(bas_ou_gauche)
		    caca=ergebnis[150+p_y][p_z].gauche.y_et_z().y;
		else
		    caca=ergebnis[150+p_y][p_z].bas.y_et_z().y;
	    else
		if(bas_ou_gauche)
		    caca=ergebnis[150+p_y][p_z].gauche.y_et_z().z;
		else
		    caca=ergebnis[150+p_y][p_z].bas.y_et_z().z;
	    return caca;
	}
	void calcule_et_sauve_intens(int ptt_y,int ptt_z,resultats resul){
	    intensite=compute_intensite(resul,lambda);
	    if(ptt_z==150&&!michelson_deregle&&!lumiere_composee&&!source_etendue){
		cece=(resul.bas.chemin-resul.gauche.chemin)/lambda;
		calcule_nb_lambda_diff(ptt_y,cece);
	    }
	    if(Math.abs(ptt_y-142)<3&&ptt_z==150){
		resul.gauche.print("ttttttt ptt_y "+ptt_y+"ptt_z "+ptt_z+"resul.gauche ");
		resul.bas.print("intensite "+(float)intensite+" cece "+(float)cece+" caca "+(float)caca+" pointeur_vers_y "+(float)pointeur_vers_y_de_l_autre+" pointeur_vers_z"+(float)pointeur_vers_z_de_l_autre+"resul.bas");
	    }
	    //if(ptt_y/10*10==ptt_y&&ptt_z/10*10==ptt_z)
	    //System.out.println("ptt_y "+ptt_y+" intensite "+(float)intensite);
	    sauve_les_intensites(ptt_y,ampere.intens,150,intensite,ptt_z);
	    if(ptt_z==150)
		int_norm_monochr=intensite_norme;
	    diff_de_ch_opt[150+ptt_y][ptt_z]=resul.gauche.chemin-resul.bas.chemin;
	    if(ptt_z==150&&ptt_y<0&&ptt_y>-10){
		System.out.println("ùùùùùùùù  ptt_y "+ptt_y+" diff_de_ch_opt[150+ptt_y][ptt_z] "+(float)diff_de_ch_opt[150+ptt_y][ptt_z]+" pointeur_vers_y_de_l_autre "+pointeur_vers_y_de_l_autre+" intensite "+(float)intensite);
		resul.gauche.print("resul.gauche ");
		resul.bas.print("resul.bas ");
	    }
	}
	double intensite_en_fonction_de_diff_chemins(double diff_chemins,double lam){
	    return (1.+Math.cos(diff_chemins*2*pi/lam));
	}
	double compute_intensite(resultats r_g,double lambd){
	    /*
	    cece=r_g.gauche.chemin*2*pi/lambd;
	    cucu=r_g.bas.chemin*2*pi/lambd;
	    toto.assigne(Math.cos(cece)+Math.cos(cucu),Math.sin(cece)+Math.sin(cucu));
	    return toto.longueur_carre();
	    */
	    if(r_g.gauche.chemin>=0.&&r_g.bas.chemin>0.){
		cece=(r_g.bas.chemin-r_g.gauche.chemin);
		cucu=Math.abs(r_g.bas.chemin-r_g.gauche.chemin)/lambd;
		caca=(cucu-(int)cucu)*2*pi;
	    }else
		caca=pi; //pour faire une amplitude nulle en cas d'echec(chemin =-1000.)
	    return (1.+Math.cos(caca));
	}
	point_y_z matcher_les_rayons_source_etendue(point_y_z pt_y_z_a_matcher){
	    if(!michelson_deregle){
		resu.gauche.assigne(autre_amplitude_rayons_differents(150,pt_y_z_a_matcher,2,ergebnis));//oui, c'est bien 2, car dans ce cas on se refere à celui du bas, sur lequel on a regle celui de gauche

	    }else{
		if(miroir_gauche_deregle){
		    if(miroir_bas_deregle){
			resu.gauche.assigne(autre_amplitude_rayons_differents(150,pt_y_z_a_matcher,2,ergebnis));
		    }else{
			resu.gauche.assigne(autre_amplitude_rayons_differents(150,pt_y_z_a_matcher,2,ergebnis));
		    }
		}else{
		    if(miroir_bas_deregle){
			resu.bas.assigne(autre_amplitude_rayons_differents(150,pt_y_z_a_matcher,1,ergebnis));
		    }
		}
	    }
	    toto_y_z.y=pointeur_vers_y_de_l_autre;
	    toto_y_z.z=pointeur_vers_z_de_l_autre;
	    return toto_y_z;
	}
	resultats matcher_les_rayons_sur_l_ecran(int ptt_y,int ptt_z){
	    if(!michelson_deregle||miroir_gauche_deregle){
		toto_bool_autre_am=ptt_z==150&&Math.abs(ptt_y-142)<3;
		resu.gauche.assigne(autre_amplitude_rayons_differents(ptt_z,ergebnis[150+ptt_y][ptt_z].bas.y_et_z(),1,ergebnis));
		resu.bas.assigne(ergebnis[150+ptt_y][ptt_z].bas);
		bas_ou_gauche=false;//donc le bas
		/*
		if(toto_bool_autre_am){
		    ergebnis[150+ptt_y][ptt_z].gauche.print("nnnnn ptt_y "+ptt_y+" ptt_z "+ptt_z+" ergebnis[150+ptt_y][ptt_z].gauche");
		    coco=(resu.gauche.chemin);
		    cece=(resu.gauche.y);
		    cucu=(resu.gauche.z);
		    System.out.println("ptt_y "+ptt_y+" gauche  coco "+(float)coco+" cece "+(float)cece+" cucu "+(float)cucu);
		    coco=(resu.bas.chemin);
		    cece=(resu.bas.y);
		    cucu=(resu.bas.z);
		    System.out.println("ptt_z "+ptt_z+" bas  coco "+(float)coco+" cece "+(float)cece+" cucu "+(float)cucu);
		}
		*/
	    }else{
		if(miroir_bas_deregle){
		    resu.bas.assigne(autre_amplitude_rayons_differents(ptt_z,ergebnis[150+ptt_y][ptt_z].gauche.y_et_z(),2,ergebnis));
		    resu.gauche.assigne(ergebnis[150+ptt_y][ptt_z].gauche);
		    bas_ou_gauche=true;
		}
	    }
	    return resu;
	}
	class resultats{
	    gauchee gauche;bass bas;
	    public  resultats(){
		gauche=new gauchee();bas=new bass();
	    }
	    void assigne(resultats r){
		gauche.chemin=r.gauche.chemin;
		gauche.y=r.gauche.y;
		gauche.z=r.gauche.z;
		bas.chemin=r.bas.chemin;
		bas.y=r.bas.y;
		bas.z=r.bas.z;
	    }
	    void remise_a_zero(){
		gauche.chemin=0.;
		gauche.y=0.;
		gauche.z=0.;
		bas.chemin=0.;
		bas.y=0.;
		bas.z=0.;
	    }
	    class gauchee{
		double chemin=0.,y=0.,z=0.;
		public gauchee(){
		}
		public void assigne(gauchee g){
		    chemin=g.chemin;
		    y=g.y;
		    z=g.z;
		}
		public void assigne(point pt){
		    chemin=pt.x;
		    y=pt.y;
		}
		public void assigne(triple_double tr){
		    chemin=tr.r;
		    y=tr.v;
		    z=tr.b;
		}
		public point_y_z y_et_z(){
		    toto_y_z.assigne(y,z);
		    return toto_y_z;
		}
		public point amplitude(double lambdaa){
		    toto.assigne(Math.cos(chemin*2*pi/lambdaa),Math.sin(chemin*2*pi/lambdaa));  
		    return toto;
		}
		public void print(String st){
		    System.out.println(st+" chemin"+(float)chemin+" y "+(float)y+" z "+(float)z);
		}
	    }
	    class bass{
		double chemin=0.,y=0.,z=0.;
		public bass(){
		}
		public void assigne(bass b){
		    chemin=b.chemin;
		    y=b.y;
		    z=b.z;
		}
		public void assigne(point pt){
		    chemin=pt.x;
		    y=pt.y;
		}
		public void assigne(triple_double tr){
		    chemin=tr.r;
		    y=tr.v;
		    z=tr.b;
		}
		public point amplitude(double lambdaa){
		    toto.assigne(Math.cos(chemin*2*pi/lambdaa),Math.sin(chemin*2*pi/lambdaa));  
		    return toto;
		}
		public point_y_z y_et_z(){
		    toto_y_z.assigne(y,z);
		    return toto_y_z;
		}
		public void print(String st){
		    System.out.println(st+" chemin "+(float)chemin+" y "+(float)y+" z "+(float)z);
		}
	    }
	}
    }


    abstract class fabry_perot_ou_lame extends fabry_perot_ou_lame_ou_mich{
	double cacarac=0.,cacarac2=0.,y_amont=0.;
	point_coeff pc_fin;double coef_init[]=new double[300];
	double facteur_approx=0;
	fabry_perot_ou_lame(){
	    perot_lame=true;
	    echelle_transverse=100.;
	    echelle_transverse0=100.;
	    pc_fin=new point_coeff(0.,0.,0.);
	    int ig=0,ptt_z=0;
	    for(int ptt_y=-150;ptt_y<150;ptt_y++)
		for(ptt_z=ii_zmin;ptt_z<ii_zmax;ptt_z++){
		    position_y_z[ptt_y+150][ptt_z]=new point_y_z(0.,0.);
		    amplitude_source_ponctuelle[ptt_y+150][ptt_z]=new point_y_z(0.,0.);
		}
	    angle_coin[0]=0.00001;
	    angle_coin[1]=0.00002;
	    angle_coin[2]=0.00005;
	    angle_coin[3]=0.0001;
	    angle_coin[4]=0.0002;

	    angle_coin_string[0]="angle coin 0.00001rd";
	    angle_coin_string[1]="angle coin 0.00002rd";
	    angle_coin_string[2]="angle coin 0.00005rd";
	    angle_coin_string[3]="angle coin 0.0001rd";
	    angle_coin_string[4]="angle coin 0.0002rd";
	    for(int ik=0;ik<5;ik++)
		angle_coin_item[ik]=new MenuItem(angle_coin_string[ik]);
	}
	void traite_command(){
	    traite_comm();
	    for (int i=0;i<5;i++){
		if(command==angle_coin_string[i]){
		    initialise_menu_modifier();
		    obj_perot_lame.calculs_face_penchee(-angle_coin[i],obj_perot_lame.a_peu_pres_horizontal);
		    obj_perot_lame.leger_coin=true;
		    faces_perot_lame_non_paralleles=true;
		    angle_coin0=-angle_coin[i];
		    menu_vib.setEnabled(true);
		    itep_composee.setEnabled(true);
		    barre_des_menus();
		    lame_mais_miroirs_non_perp=true;
		    break;
		}
	    }
	    for (int i=0;i<10;i++){
		if(command==facteur_refl_[i]){
		    initialise_menu_modifier();
		    calcule_facteurs_de(fact_ref[i]);
		    menu_vib.setEnabled(true);
		    itep_composee.setEnabled(true);
		    barre_des_menus();
		    break;
		}
	    }
	}
	void intensites_de_base(){
	    System.out.println(" rrrr entree intensites_de_base perot ou lame ");
	    if(!ne_rien_dessiner){
		dessine_parcours=true;
		if(interferon==fbr_prt)
		    calcul_des_chemins_transmission(-(int)(5.*30./obj_perot_lame.carac),false);
		else
		    calcul_des_chemins_transmission(-15,false);
		dessine_parcours=false;
	    }
	    int ig=0,ii_z=0;
	    if(!vibration){
		for(int ii_y=-150;ii_y<150;ii_y++){
		    for(ii_z=ii_zmin;ii_z<ii_zmax;ii_z++){
			amplitude_source_ponctuelle[ii_y+150][ii_z].assigne(0.,0.);
			ampere.intens[ii_y+150][ii_z]=(float)0.;
		    }
		    calcul_des_chemins_transmission(ii_y,true);
		}		    
	    }
	    for(int ptt_y=-149;ptt_y<150;ptt_y++){
		ypoint[150+ptt_y]=photom.pt.y+ptt_y;
		chemin_optique=0.;				
		if(lentle_2!=null){
		    y_du_PM=photom.pt.y-ptt_y/echelle_transverse;
		    coef_ang_init=(y_du_PM-photom.pt.y)/(photom.pt.x-lentle_2.pt.x);
		    intensite=chemin_opt_et_intensite_a_l_image(ptt_y,coef_ang_init);
		    if(vibration){
			sauve_les_intensites(ptt_y,ampere.intens,150,intensite,150);
			int_norm_monochr=intensite_norme;
		    }
		}
		if(!vibration)
		    calcul_des_chemins_transmission(ptt_y,false);
	    }
	    if(!vibration){
		calcule_et_sauve_intens();
		if(lentle_2==null)
		    for(int j=0;j<3;j++)
			lisse_les_intensites();
	    }
	}
	void calcule_facteurs_de(double f){
	    if(perot_fabryyyy||lame_reflexion){
		obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_externe=-f;
		obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne=-f;
		System.out.println("dans facterus_de  obj_perot_lame "+obj_perot_lame+ " obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne "+obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne+" f "+f);
		obj_perot_lame.pouvoir_miroir_transmetteur_en_amplitude_externe=f;
		obj_perot_lame.pouvoir_miroir_transmetteur_en_amplitude_interne=f;
		facteur_de_reflexion_miroir_en_puissance_externe=Math.pow(obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_externe,2);
		facteur_de_reflexion_miroir_en_puissance_interne=Math.pow(obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne,2);
		facteur_de_transmission_miroir_en_puissance_externe=Math.pow(obj_perot_lame.pouvoir_miroir_transmetteur_en_amplitude_externe,2);
		facteur_de_transmission_miroir_en_puissance_interne=Math.pow(obj_perot_lame.pouvoir_miroir_transmetteur_en_amplitude_interne,2);
	    }
	    if(!perot_fabryyyy){
		caca=obj_perot_lame.carac2;
		obj_perot_lame.pouvoir_reflecteur_en_amplitude_externe=(caca-1)/(caca+1);
		obj_perot_lame.pouvoir_reflecteur_en_amplitude_interne=(1./caca-1)/(1./caca+1);
		obj_perot_lame.pouvoir_transmetteur_en_amplitude_externe=2*caca/(caca+1);
		obj_perot_lame.pouvoir_transmetteur_en_amplitude_interne=2/(caca+1);
		facteur_de_reflexion_en_puissance_externe=Math.pow(obj_perot_lame.pouvoir_reflecteur_en_amplitude_externe,2);
		facteur_de_reflexion_en_puissance_interne=Math.pow(obj_perot_lame.pouvoir_reflecteur_en_amplitude_interne,2);
		facteur_de_transmission_en_puissance_externe=Math.pow(obj_perot_lame.pouvoir_transmetteur_en_amplitude_externe,2);
		facteur_de_transmission_en_puissance_interne=Math.pow(obj_perot_lame.pouvoir_transmetteur_en_amplitude_interne,2);
	    }
	}
	void lisse_les_intensites(){
	    int j=0;amperes ampere_prov=(amperes)ampere.clone(); 
	    for(int ptt_y=-148;ptt_y<149;ptt_y++)		
		for(j=ii_zmin;j<ii_zmax;j++)
		    if(ii_zmin==ii_zmax-1)
			ampere.intens[ptt_y+150][j]=(ampere_prov.intens[ptt_y+150+1][j]+ampere_prov.intens[ptt_y+150][j]+ampere_prov.intens[ptt_y+150-1][j])/(float)3.;
		    else if(j!=ii_zmin&&j!=ii_zmax-1){
			ampere.intens[ptt_y+150][j]=(ampere_prov.intens[ptt_y+150+1][j]+ampere_prov.intens[ptt_y+150+1][j+1]+ampere_prov.intens[ptt_y+150+1][j-1]+ampere_prov.intens[ptt_y+150][j]+ampere_prov.intens[ptt_y+150][j+1]+ampere_prov.intens[ptt_y+150][j-1]+ampere_prov.intens[ptt_y+150-1][j+1]+ampere_prov.intens[ptt_y+150-1][j]+ampere_prov.intens[ptt_y+150-1][j-1])/(float)9.;
			/*
			if(Math.abs(ptt_y-90)<4&&Math.abs(j-150)<=1)
			    System.out.println("qqqqqqq ptt_y "+ptt_y+" j "+j+" ampere.intens[ptt_y+150][j] "+(float)ampere.intens[ptt_y+150][j]);
			*/
		    }
	}
	void calcule_et_sauve_intens(){
	    intensite_norme=0;
	    for(int ptt_y=-149;ptt_y<150;ptt_y++)		
		for(int j=ii_zmin;j<ii_zmax;j++){
		    intensite=amplitude_source_ponctuelle[ptt_y+150][j].longueur_carre();
		    if(j==150&&ptt_y/20*20==ptt_y&&interferon==lame_par_reflex)
			amplitude_source_ponctuelle[ptt_y+150][j].print(" ptt_y "+ptt_y +" j "+j+" intensite "+(float)intensite+" ampl ");
		    sauve_les_intensites(ptt_y,ampere.intens,150,intensite,j);
		    if(j==150){
			int_norm_monochr=intensite_norme;
			if(lentle_2!=null){
			    cece=diff_de_ch_opt[150+ptt_y][150]/lambda;
			    calcule_nb_lambda_diff(ptt_y,cece);
			}
		    }
		}
	    System.out.println(" intensite_norme "+intensite_norme);
	}
	void calcul_des_chemins_transmission(int po,boolean initialisation){ 
	    //p_y_z.assigne(ptt_y*facteur_approx,(ptt_z-150)*facteur_approx);
	    if(!dessine_parcours)
		if(lentle_2==null)
		    obj_perot_lame.y_sur_le_plan_amont=obj_perot_lame.pt.y+po/echelle_transverse*(obj_perot_lame.pt.x-src.pt.x)/(photom.pt.x-src.pt.x);
		else
		    obj_perot_lame.y_sur_le_plan_amont=obj_perot_lame.pt.y+po/echelle_transverse*(obj_perot_lame.pt.x-src.pt.x)/(photom.pt.x-lentle_2.pt.x);
	    //	obj_perot_lame.y_sur_le_plan_amont=obj_perot_lame.pt.y+po/echelle_transverse;
	    else
		obj_perot_lame.y_sur_le_plan_amont=obj_perot_lame.pt.y+po;
	    if(dessine_parcours){
		toto.assigne(obj_perot_lame.pt.x,obj_perot_lame.y_sur_le_plan_amont);
		drawlinj(src.pt,toto,Color.red);
	    }
	    pc_util.pt.assigne(obj_perot_lame.pt.x,obj_perot_lame.y_sur_le_plan_amont);
	    //if(po/10*10==po)
	    //pc_util.print("po "+po+" pc_util ");
	    pt_B.assigne_soustrait(pc_util.pt,src.pt);
	    //pc_zz=0.25*19.4444/12.499866*(j-150)/echelle_transverse;
	    for(int j=ii_zmin;j<ii_zmax;j++){
		ou_j_en_suis=" de la source a la lame";
		chemin_optique_avec_z=0.;
		//pc_zz=(j+0.5-150)/echelle_transverse*(obj_perot_lame.pt.x-src.pt.x)/(photom.pt.x-src.pt.x);
		pc_zz=(j-150)/echelle_transverse*(obj_perot_lame.pt.x-src.pt.x);
		if(lentle_2==null)
		    pc_zz/=(photom.pt.x-src.pt.x);
		else
		    pc_zz/=(photom.pt.x-lentle_2.pt.x);
		//pc_zz=(j-150)/echelle_transverse;
		pc_z_prec=0.;
		if(po==100&&j==150){
		    System.out.println(" ");
		    System.out.println(" po "+po+" j "+j+"y_sur_le_plan_amont "+(float)obj_perot_lame.y_sur_le_plan_amont);
		    pt_B.print_float(" pc_zz "+(float)pc_zz+" pt_B " );
		}
		c_dir.assigne(0.,1.,0.);//pour pc_zz qui ne doit pas être augmente
		drawlinf_somme_z(pc_util.pt,src.pt,c_dir,1.,j,po);
		coco=Math.sqrt(pt_B.longueur_carre()+pc_zz*pc_zz);
		c_dir.assigne(pt_B.x/coco,pt_B.y/coco,pc_zz/coco);
		c_dir_init.assigne(c_dir);
		y_amont=obj_perot_lame.y_sur_le_plan_amont;
		c_dir.apres_diffraction_rel_x(obj_perot_lame.indice);
		//c_dir_garde.print(" c_dir_garde apres diffraction ");
		facteur_d_amplitude=1.;
		for(i_rayon=0;i_rayon<nombre_de_rayons;i_rayon++){
		    //if((dessine_parcours&&j==150||(po==-36&&(j==65||j==38))&&!vibration))
		    //	System.out.println(" yyyyyyyy i_rayon "+i_rayon+" po "+po+" j "+j+" facteur_d_amplitude "+(float)facteur_d_amplitude);
		    ou_j_en_suis=" traversee de la lame";
		    toto_pt_coeff.pt.assigne(obj_perot_lame.pt.x,y_amont);
		    toto_pt_coeff.coeff=c_dir.y_sur_x();
		    tata.assigne(obj_perot_lame.pt);
		    tata.x+=obj_perot_lame.carac;
		    if(Math.abs(obj_perot_lame.coefff)<1e10)
			pt_lame.assigne(intersection_avec_plan(tata,obj_perot_lame.coefff,toto_pt_coeff));
		    else
			pt_lame.assigne(obj_perot_lame.x_sur_le_plan_aval,y_amont+obj_perot_lame.carac*toto_pt_coeff.coeff);
		    drawlinf_somme_z(pt_lame,toto_pt_coeff.pt,c_dir,obj_perot_lame.indice,j,po);
		    c_dir_garde.assigne(c_dir);
		    pc_zz_garde=pc_zz;
		    chemin_optique_avec_z_garde=chemin_optique_avec_z;
		    ou_j_en_suis=" vers l'ecran ";
		    if(!(interferon==fbr_prt)){
			c_dir.pt.rotation_x(obj_perot_lame.u_normale_face_penchee,false);
			c_dir.apres_diffraction_rel_x(1./obj_perot_lame.indice);
			c_dir.pt.rotation_x(obj_perot_lame.u_normale_face_penchee,true);
		    }
		    pt_y_z_ecran.assigne(sortie_z_bis(j,po,false,pt_lame));
		    if(!dessine_parcours)
			if(vas_y_pour_l_amplitude(po,j,initialisation,facteur_d_amplitude))
			    break;
 		    if(i_rayon!=nombre_de_rayons-1){
			ou_j_en_suis=" retour ";
			c_dir.assigne(c_dir_garde);
			/*
			if(((po==100&&j==250)||po==-100&&j==50||Math.abs(po-7)<=1&&j==150)&&!vibration)
			    c_dir.pt.print("ùùùùùùùùùùùùùùùùùùùùù po "+po+" j "+j+" c_dir.pt.scalaire(u_normale_face_penchee "+c_dir.pt.scalaire(obj_perot_lame.u_normale_face_penchee)+" c_dir.pt.vectoriel(u_normale_face_penchee "+c_dir.pt.vectoriel(obj_perot_lame.u_normale_face_penchee)+"c_dir.pt avant");
			*/
			c_dir.pt.symetrique_retour(obj_perot_lame.u_normale_face_penchee);
			/*
			if(((po==100&&j==250)||po==-100&&j==50||Math.abs(po-7)<=1&&j==150)&&!vibration)
			    c_dir.pt.print("ùùùùùùùùùùùùùùùùùùùùù po "+po+" j "+j+" c_dir.pt.scalaire(u_normale_face_penchee "+c_dir.pt.scalaire(obj_perot_lame.u_normale_face_penchee)+" c_dir.pt.vectoriel(u_normale_face_penchee "+c_dir.pt.vectoriel(obj_perot_lame.u_normale_face_penchee)+" c_dir.pt apres");
			*/
			coco=obj_perot_lame.pt.x;
			tutu.assigne(coco,pt_lame.y+(coco-pt_lame.x)*c_dir.y_sur_x());
			pc_zz=pc_zz_garde;
			pc_z_prec=pc_zz;
			chemin_optique_avec_z=chemin_optique_avec_z_garde;
			drawlinf_somme_z(tutu,pt_lame,c_dir,obj_perot_lame.indice,j,po);
			c_dir.pt.x=-c_dir.pt.x;
			//c_dir.print(" c_dir second round ");
			y_amont=tutu.y;
		    }
		    if(perot_fabryyyy)
			facteur_d_amplitude*=facteur_de_reflexion_miroir_en_puissance_interne;
		    else
			facteur_d_amplitude*=facteur_de_reflexion_en_puissance_interne;
		}
	    }
	}
	boolean vas_y_pour_l_amplitude(int po,int j,boolean initialisation,double fct){
	    coco=chemin_optique_avec_z*cms_par_pixels*2*pi/lambda;
	    toto_y_z.assigne(Math.cos(coco),Math.sin(coco));
	    if(initialisation){
		if(j==150||j==140||j==160||j==130||j==170)
		    chm_opti[po+150][j/10-13]=chemin_optique_avec_z;
		amplitude_source_ponctuelle[po+150][j].assigne_facteur(toto_y_z,fct);
		position_y_z[po+150][j].assigne(pt_y_z_ecran);
		if(j==20&&po==-140)
		    position_y_z[po+150][j].print(" i_rayon "+i_rayon+" po "+po+" j "+j+ "position_y_z[po+150][j] ");
		return true;
	    }else{
		if(i_rayon==0){
		    sens_pos_y=1;
		    if(pt_y_z_ecran.y<position_y_z[150][150].y)
			sens_pos_y=-1;
		    if(interferon==lame_par_reflex)
		    	sens_pos_y=1;
		    if(!faces_perot_lame_non_paralleles)
			p_y_deb=po-sens_pos_y*5;
		    else{
			sens_pos_y=1; 
			p_y_deb=-148;  
		    }
		    if(p_z_deb<-148)
			p_z_deb=-148;
		    if(p_z_deb>148)
			p_z_deb=148;
		    sens_pos_z=1;
		    if(pt_y_z_ecran.z<position_y_z[150][150].z)
			sens_pos_z=-1;
		    p_z_deb=j-sens_pos_z*5;	
		    if(p_z_deb<1)
			p_z_deb=1;
		    if(p_z_deb>298)
			p_z_deb=298;
		    //if(po==-36&&(j==65||j==38)&&!vibration){
		    if(po==-140&&j==20&&!vibration){
			pt_y_z_ecran.print("wwwwwwwwwww  premier rayon po "+po+" j "+j+" pt_y_z_ecran ");
			obj_perot_lame.u_normale_face_penchee.print(" obj_perot_lame.u_normale_face_penchee ");
		    }
		    
		}else{

		    if(po==100&&j==250&&!vibration){
			//if(((po==100&&j==250)||po==-100&&j==50||po==0&&j==150)&&!vibration){
			coco=chemin_optique_avec_z*2*pi/lambda*cms_par_pixels;
			//pt_y_z_ecran.print("xxxxxxxxx i_rayon "+i_rayon+" po "+po+" j "+j+" pt_y_z_ecran ");
			toto.assigne(Math.cos(coco),Math.sin(coco));
			toto.print(" toto.longueur_carre() "+(float)toto.longueur_carre()+ " toto ");
		    }
		    toto_boolean=distribue_amplitude_sur_les_voisins(pt_y_z_ecran,fct,po,j,i_rayon,toto_y_z);
		    if(!toto_boolean)
			return true;
		}
	    }
	    return false;
	}
	double chemin_opt_et_intensite_a_l_image(int pt_y,double coef_ang_ini){
	    double inten=0.;
	    cacarac=obj_perot_lame.carac;
	    cacarac2=obj_perot_lame.carac2;
	    sinus_interne_lame=coef_ang_ini/Math.sqrt((1.+coef_ang_ini*coef_ang_ini));
	    sinus_externe=sinus_interne_lame;
	    if(pt_y==-148)
		System.out.println(" cacarac "+cacarac+" cacarac2 "+cacarac2+" cms_par_pixels "+cms_par_pixels);
	    obj_perot_lame.x_sur_le_plan_aval=obj_perot_lame.pt.x+obj_perot_lame.carac;	    
	    if(lame_transmission){
		sinus_interne_lame/=cacarac2;
		pc_util.coeff=sinus_interne_lame/Math.sqrt(1.-sinus_interne_lame*sinus_interne_lame);
		tg_interne_lame=pc_util.coeff;
		caca=1./Math.sqrt(1.+tg_interne_lame*tg_interne_lame);
		diff_de_ch_opt[150+pt_y][150]=2.*cacarac*cacarac2*caca;
		diff_de_ch_opt[150+pt_y][150]*=cms_par_pixels;// 1 pixel=0.2cm.
		//System.out.println(" sinus_interne_lame "+sinus_interne_lame+" pc_util.coeff "+pc_util.coeff);
		aoao=facteur_de_reflexion_en_puissance_interne;
		inten=(float)(1.+aoao*aoao+2.*aoao*Math.cos(diff_de_ch_opt[150+pt_y][150]*2*pi/lambda));
			    //inten=1.+facteur_de_reflexion_en_puissance_externe*facteur_de_reflexion_en_puissance_externe+2*facteur_de_reflexion_en_puissance_externe*Math.cos(diff_de_ch_opt[150+pt_y][150]*2*pi/lambda);
		//if(pt_y/10*10==pt_y){
		//    pc_util.print("coef_ang_ini "+(float)coef_ang_ini+"caca "+caca+"pc_util");
		//	    }
	    }else{
		diff_de_ch_opt[150+pt_y][150]=2.*cacarac/Math.sqrt(1.+coef_ang_ini*coef_ang_ini);
		diff_de_ch_opt[150+pt_y][150]*=cms_par_pixels;
		aoao=facteur_de_reflexion_miroir_en_puissance_interne;
		inten=(float)(1./(1.+aoao*aoao-2.*aoao*Math.cos(diff_de_ch_opt[150+pt_y][150]*2*pi/lambda)));
		    //inten=1./(1.+facteur_de_reflexion_en_puissance_externe*facteur_de_reflexion_en_puissance_externe-2*facteur_de_reflexion_en_puissance_externe*Math.cos(diff_de_ch_opt[150+pt_y][150]*2*pi/lambda));
	    }
	    aoao=diff_de_ch_opt[150+pt_y][150]/lambda;
	    if(pt_y/10*10==pt_y)
		System.out.println("pt_y "+pt_y+" coef_ang_ini "+coef_ang_ini+" aoao "+aoao);
	    calcule_nb_lambda_diff(pt_y,aoao);
	    return inten;
	}
    }
    class fabry_perot extends fabry_perot_ou_lame{
	bi_miroirs_paralleles bi_mirr;
	fabry_perot(){
	    super();
	    src=new source(20.,y_banc,0.,0,la_source,0.,0.,0.,nb_objets);
	    num_obj_laser=nb_objets-1;
	    bi_mirr=new bi_miroirs_paralleles(60.,y_banc,0.,80,le_perotfabry,20.,0.7,0.,nb_objets);
	    perot_fabryyyy=true;
	    num_obj_perot_lame=nb_objets-1;
	    obj_perot_lame=bi_mirr;
	    num_obj_perot=nb_objets-1;
	    photom=new PM(380.,y_banc,0.,200,le_PM,0.,0.,0.,nb_objets);
	    nombre_de_rayons=8;
	    calcule_facteurs_de((double)0.707);
	    System.out.println(" obj_perot_lame "+obj_perot_lame+ " obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne "+obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne);
	}
	double intensite_en_fonction_de_diff_chemins(double diff_chem,double lambd ){
	    rr=fbr_prt.bi_mirr.pouvoir_reflecteur_en_amplitude_externe*fbr_prt.bi_mirr.pouvoir_reflecteur_en_amplitude_externe;
	    return 1./(1.+rr*rr-2*rr*Math.cos(diff_chem*2*pi/lambd));
	}
    }
    abstract class lame_trans_ou_reflex extends fabry_perot_ou_lame{
	lame_trans_ou_reflex(){
	    super();
	    perot_lame=true;
	    photom=new PM(380.,y_banc,0.,200,le_PM,0.,0.,0.,nb_objets);
	}
	double intensite_en_fonction_de_diff_chemins(double diff_chem,double lambd){
	    rr=lame_semi.pouvoir_reflecteur_en_amplitude_externe*lame_semi.pouvoir_reflecteur_en_amplitude_externe;
	    return 1.+rr*rr+2*rr*Math.cos(diff_chem*2*pi/lambd);
	}
    }
    class lame_par_transmission extends lame_trans_ou_reflex{
	lame_par_transmission(){
	    super();
	    lame_transmission=true;
	    src=new source(20.,y_banc,0.,40,la_source,-0.25,0.,0.,nb_objets);
	    num_obj_laser=nb_objets-1;
	    lame_semi=new lame_semireflechissante(60.,y_banc,0.,80,lame_semireflechissante,4.,1.5,1.,nb_objets);
	    //en fait, cette "lame_semi" n'est qu'une lame à faces paralleles. On peut imaginer la metalliser sur son cote gauche, mais cela ne parait pas interessant.
	    num_obj_perot_lame=nb_objets-1;
	    obj_perot_lame=lame_semi;
	    lentle_2=new lentille(190.,y_banc,0.,100,la_lentille,photom.pt.x-190.,0.,0.,nb_objets);
	    itep_mettre_lentille_finale=new MenuItem("enlever la lentille sur le trajet final.");
	    cms_par_pixels=0.02;cms_par_pixels0=0.02;
	    echelle_transverse=10;
	    nombre_de_rayons=2;
	    calcule_facteurs_de(0.);
	}
    }
    class lame_par_reflexion extends lame_trans_ou_reflex{
	double coef_ang_init0=0.;boolean pas_trouve[]=new boolean [300]; 
	point pt_init,pt_vers_l_ecran;double epaisseur_reference=-4.;
	lame_par_reflexion(){
	    super();
	    lame_reflexion=true;
	    photom.pt.x=300.;// new PM!!!
	    src=new source(20.,y_banc,pi/2.,40,la_source,+0.707,0.,0.,nb_objets);
	    num_obj_laser=nb_objets-1;
	    //lame_semi=new lame_semireflechissante(110.,260,pi/2,90,lame_semireflechissante,-5.,2.,0.9,nb_objets);
	    lame_semi=new lame_semireflechissante(110.,260,pi/2,90,lame_semireflechissante,epaisseur_reference,1.5,0.9,nb_objets);
	    num_obj_perot_lame=nb_objets-1;
	    obj_perot_lame=lame_semi;
	    cms_par_pixels0=0.2/400.;
	    cms_par_pixels=cms_par_pixels0;
	    echelle_transverse=10.;
	    echelle_transverse0=10.;
	    pt_init=new point(0.,0.);pt_vers_l_ecran=new point(0.,0.);
	    if(plaque_photo!=null)
		plaque_photo.setVisible(false);
	    if(sp_couleur!=null)
		sp_couleur=null;
	    nombre_de_rayons=6;
	    calcule_facteurs_de(0.7);
	}
	void intensites_de_base(){
	    System.out.println(" toto ");
	    coef_ang_init0=0.5;
	    cacarac=Math.abs(lame_semi.carac);
	    cacarac2=lame_semi.carac2;
	    coco=2.*(lame_semi.pt.y+lame_semi.carac-src.pt.y);
	    caca=(photom.pt.x-lame_semi.pt.x);
	    for(int ii_y=0;ii_y<300;ii_y++)
		if(lentle_2==null)
		    pas_trouve[ii_y]=true;
		else
		    pas_trouve[ii_y]=false;
	    if(lentle_2==null)
		y_centre=src.pt.y+2.*(lame_semi.pt.y+lame_semi.carac-src.pt.y)-(photom.pt.x-src.pt.x)*coef_ang_init0;
	    else
		y_centre=lentle_2.pt.y-lentle_2.carac*coef_ang_init0;
	    System.out.println(" y_centre "+(float)y_centre+" coco "+coco+" caca "+(float)caca);
	    //objet[223]=null;
	    lame_semi.x_sur_le_plan_amont=src.pt.x+(lame_semi.pt.y+lame_semi.carac-src.pt.y)/coef_ang_init0;
	    lame_semi.y_sur_le_plan_aval=lame_semi.pt.y;
	    lame_semi.y_sur_le_plan_amont=lame_semi.pt.y+lame_semi.carac;//attention ce carac est negatif
	    if(!ne_rien_dessiner){
		dessine_parcours=true;
		determiner_ii_z_min_max(true);
		calcul_des_chemins_reflexion(0,false);
		determiner_ii_z_min_max(false);
		dessine_parcours=false;
	    }
	    pt_y_z_ecran.print("fffffff pt_y_z_ecran ");  
	    int ig=0,ii_z=0;
	    for(int ii_y=-150;ii_y<150;ii_y++){
		pas_trouve[ii_y+150]=true;
		for(ii_z=ii_zmin;ii_z<ii_zmax;ii_z++){
		    amplitude_source_ponctuelle[ii_y+150][ii_z].assigne(0.,0.);
		    ampere.intens[ii_y+150][ii_z]=(float)0.;
		}
		calcul_des_chemins_reflexion(ii_y,true);
		if(Math.abs(ii_y+140)<3)
		    amplitude_source_ponctuelle[ii_y+150][150].print("ddddddddddd ii_y "+ii_y +" j=150  ampl ");
		if(ii_y==0)
		    position_y_z[150][150].print("dddddddddd ii_y "+ii_y+ "position_y_z[150][150] ");
	    }
	    for(int ptt_y=-149;ptt_y<150;ptt_y++){
		//chemin_optique=0.;		
		ypoint[150+ptt_y]=y_centre+ptt_y;
		//obj_perot_lame.y_sur_le_plan_amont=obj_perot_lame.pt.y+ptt_y;
		calcul_des_chemins_reflexion(ptt_y,false);
	    }
	    calcule_et_sauve_intens();
	}
	void calcul_des_chemins_reflexion(int po,boolean initialisation){
	    if(lentle_2==null)
		coef_ang_init=(src.pt.y+2.*(lame_semi.pt.y+lame_semi.carac-src.pt.y)-(y_centre+po/echelle_transverse))/(photom.pt.x-src.pt.x);
	    else
		coef_ang_init=-(y_centre-lentle_2.pt.y+po/echelle_transverse)/(photom.pt.x-lentle_2.pt.x);
	    lame_semi.x_sur_le_plan_amont=src.pt.x+(lame_semi.pt.y+lame_semi.carac-src.pt.y)/coef_ang_init;
	    if(po/20*20==po)
		System.out.println(" po "+po+" lame_semi.x_sur_le_plan_amont "+(float)lame_semi.x_sur_le_plan_amont+" coef_ang_init "+(float)coef_ang_init);
	    unite_z=(lame_semi.x_sur_le_plan_amont-src.pt.x)/echelle_transverse;
	    if(lentle_2==null)
		unite_z/=(photom.pt.x-src.pt.x);
	    else
		unite_z/=(photom.pt.x-lentle_2.pt.x);
	    pc_util.pt.assigne(obj_perot_lame.x_sur_le_plan_amont,obj_perot_lame.y_sur_le_plan_amont);
	    pt_B.assigne_soustrait(pc_util.pt,src.pt);
	    if(po==100){
		System.out.println(" ");
		pt_B.print_float(" po "+po+" pt_B " );
		pc_util.print("coef_ang_init "+(float)coef_ang_init+" y_centre "+(float)y_centre+" pc_util " );
		toto.print_float(" toto ");
	    }
	    
	    //pc_zz=0.25*19.4444/12.499866*(j-150)/echelle_transverse;
	    for(int j=ii_zmin;j<ii_zmax;j++){
		i_rayon=0;
		ou_j_en_suis=" de la source a la lame";
		chemin_optique_avec_z=0.;
		facteur_d_amplitude=1.;
		//pc_zz=(j-150)/echelle_transverse/(photom.pt.x-src.pt.x)*coef_ang_initit0;
		pc_zz=unite_z*(j-150);
		pc_z_prec=0.;
		c_dir.assigne(0.,1.,0.);//pour pc_zz qui ne doit pas être augmente
		drawlinf_somme_z(pc_util.pt,src.pt,c_dir,1.,j,po);
		pt_init.assigne(pc_util.pt);
		coco=Math.sqrt(pt_B.longueur_carre()+pc_zz*pc_zz);
		c_dir.assigne(pt_B.x/coco,pt_B.y/coco,pc_zz/coco);
		facteur_d_amplitude=1.;
		for(i_rayon=0;i_rayon<nombre_de_rayons;i_rayon++){
		    ou_j_en_suis=" vers l'ecran ";
		    if(i_rayon==0){
			toto.assigne_facteur(obj_perot_lame.u_normale_face_penchee,-1.);
			c_dir.pt.symetrique_retour(toto);
			pt_vers_l_ecran.assigne(pt_init);
		    }else{
			toto.assigne_facteur(obj_perot_lame.u_normale_face_penchee,-1.);
			if(lame_semi.leger_coin)
			    c_dir.pt.rotation_y(toto,true);
			c_dir.apres_diffraction_rel_y(1./obj_perot_lame.indice);
			if(!lame_semi.leger_coin)
			    c_dir.pt.y=-c_dir.pt.y;
			if(lame_semi.leger_coin)
			    c_dir.pt.rotation_y(toto,false);
		    }
		    c_dir_garde.assigne(c_dir);
		    pc_zz_garde=pc_zz;
		    pc_z_prec_garde=pc_zz;
		    chemin_optique_garde=chemin_optique_avec_z;
		    pt_y_z_ecran.assigne(sortie_z_bis(j,po,false,pt_vers_l_ecran));
		    if(!dessine_parcours){
			if(vas_y_pour_l_amplitude(po,j,initialisation,facteur_d_amplitude*obj_perot_lame.pouvoir_reflecteur_en_amplitude_externe))
			    break;
		    }
		    c_dir.assigne(c_dir_garde);
		    pc_zz=pc_zz_garde;
		    pc_z_prec=pc_z_prec_garde;
		    chemin_optique_avec_z=chemin_optique_garde;
		    if(i_rayon!=nombre_de_rayons-1){
			ou_j_en_suis=" traversee de la lame ";
			if(i_rayon==0)
			    facteur_d_amplitude=obj_perot_lame.pouvoir_transmetteur_en_amplitude_externe*obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne;
			else
			    facteur_d_amplitude*=obj_perot_lame.pouvoir_reflecteur_en_amplitude_interne*obj_perot_lame.pouvoir_miroir_reflecteur_en_amplitude_interne;
			if(lame_semi.leger_coin)
			    c_dir.pt.rotation_y(obj_perot_lame.u_normale_face_penchee,false);
			c_dir.apres_diffraction_rel_y(obj_perot_lame.indice);
			if(lame_semi.leger_coin)
			    c_dir.pt.rotation_y(obj_perot_lame.u_normale_face_penchee,true);
			pt_lame.assigne(pt_vers_l_ecran.x+cacarac*c_dir.x_sur_y(),obj_perot_lame.y_sur_le_plan_aval);
			drawlinf_somme_z(pt_lame,pt_vers_l_ecran,c_dir,obj_perot_lame.indice,j,po);
			ou_j_en_suis=" retour ";
			c_dir.pt.y=-c_dir.pt.y;
			coco=obj_perot_lame.pt.y+obj_perot_lame.carac;
			pt_vers_l_ecran.assigne(pt_lame.x-cacarac*c_dir.x_sur_y(),coco);
			drawlinf_somme_z(pt_vers_l_ecran,pt_lame,c_dir,obj_perot_lame.indice,j,po);
		    }
		}
	    }
	}
    }
    class diffraction_interferences extends interferometres{
	fentes fnte;int nombre_fentes;
	boolean ne_pas_faire_d_approx=false;
	double phase_diffraction0=0.,phase_diffraction=0,phase_diffraction_prec=0;
	int iprofondeur=0;
	final MenuItem profondeur_de[]=new MenuItem [3];
	final String profondeur_de_[]=new String[3];
	//boolean calculs_source_ponctuelle_deja_faits=false;
	diffraction_interferences(){
	    super();
	    if(i_demarre==diffraction_1_fente){
		diffraction=true;
		echelle_transverse=100.;
		echelle_transverse0=100.;
		src=new source(10.,y_banc,0.,0,la_source,0.,0.,0.,nb_objets);
		fnte=new fentes(200.,y_banc,0.,200,les_fentes,0.02,0.,1.,nb_objets);
		num_obj_fentes=nb_objets-1;
		photom=new PM(380.,y_banc,0.,200,le_PM,0.,0.,0.,nb_objets);
	    }
	    if(i_demarre==diffraction_et_interferences||i_demarre==-1&&i_ens==0){
		echelle_transverse=100.;
		echelle_transverse0=100.;
		diffraction=true;
		src=new source(10.,y_banc,0.,0,la_source,0.,0.,0.,nb_objets);
		fnte=new fentes(200.,y_banc,0.,200,les_fentes,0.02,0.1,2.,nb_objets);
		num_obj_fentes=nb_objets-1;
		photom=new PM(380.,y_banc,0.,200,le_PM,0.,0.,0.,nb_objets);
	    }
	    cms_par_pixels0=0.2;
	    cms_par_pixels=0.2;
	    profondeur_de_[0]="profondeur de 1 largeur de fentes";
	    profondeur_de_[1]="profondeur de 3 largeurs de fentes";
	    profondeur_de_[2]="profondeur de 5 largeurs de fentes";
	    for(int ik=0;ik<3;ik++)
		profondeur_de[ik]=new MenuItem(profondeur_de_[ik]);
	    System.out.println("on est bien dans diffraction ");
	}
	double intensite_en_fonction_de_diff_chemins(double diff_chemins,double lam){
	    return 0.;
	}
	void va_chercher_les_coordonnees(int pt_y,int pt_z){
	    y_ppmm=(int)Math.round(ypoint[150+pt_y]);
	    z_ppmm=(int)plaque_photo.centre.x+pt_z-150;
	}
	void calcule_surface_source_etendue(){
	    dpt_y_source_etendue=src.n_sources_y;
	    dpt_z_source_etendue=src.n_sources_z;
	    spectre_source_etendue_fait=true;  
	}
	void pouvoir_separateur(){
	    y_du_PM=objet[num_obj_pm].pt.y;
	    lambda_min=min2;
	    lambda_max=max2;
	    for(int p_lamb=0;p_lamb<300;p_lamb++){
		lambda=min2+indice_lamb*(max2-min2)/1000.+(p_lamb-500.)/1000.*(max2-min2)/10.;
		lambda=min2+p_lamb*(max2-min2)/300.;
		titi.assigne(diffr_int.Amplitude_diffraction_bis(src.pt,p_lamb,150,lambda));
		intensite = titi.longueur_carre();
		intensite=fab_lame.amplitude_source_ponctuelle[150][150].longueur_carre();
		sauve_les_intensites(p_lamb,sp_couleur.intensite_couleur,0,intensite,0);
	    }
	}
	void explique(String explic){
	    System.out.println(" explique diffraction ");
	    if(explic==""){
		toto_string="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_diffraction.jpg";
		va_chercher_et_affiche_explications(toto_string);
	    }else
	    va_chercher_et_affiche_explications(explic);
	}
	void gere_plot_intensites_1dim(int ptt_y){
	    x0_dessin=100-1;
	    fact0_dessin=-80;
	    toto.assigne(x0_dessin,ypoint[150+ptt_y]);
	    plot_intensites(toto,x0_dessin,fact0_dessin,ptt_y);
	}
	void traite_command(){
	    traite_comm();
	    boolean proff=false,impossible=false;
	    for (int i=0;i<3;i++)
		if(command==diffr_int.profondeur_de_[i]){
		    diffr_int.iprofondeur=i;
		    proff=true;
		    if(plaque_photo!=null)
			ecrit_message_important("Attendez un moment, svp.");
		    break;
		}
	    if(command=="ajouter une fente"||command=="multiplier par deux l'epaisseur des fentes"||command=="diviser par deux l'epaisseur des fentes"||command=="multiplier par deux la distance des fentes"||command=="diviser par deux la distance des fentes"||proff){
		if(command=="ajouter une fente"){
		    objet[num_obj_fentes].carac3+=1.;
		    objet[num_obj_fentes].nfentes++;
		}		    
		if(command=="multiplier par deux l'epaisseur des fentes"){
		    if(objet[num_obj_fentes].largeur_fente_en_pixels*2<objet[num_obj_fentes].distance_des_fentes_en_pixels||objet[num_obj_fentes].nfentes==1){
			objet[num_obj_fentes].largeur_fente_en_pixels*=2.;
			objet[num_obj_fentes].carac*=2.;
		    }else{
			impossible=true;
			ecrit_message_important("Impossible, la largeur serait plus grande que la distance des fentes");
		    }
		}
		if(command=="diviser par deux l'epaisseur des fentes"){
		    objet[num_obj_fentes].largeur_fente_en_pixels/=2.;
		    objet[num_obj_fentes].carac/=2.;
		}
		if(command=="multiplier par deux la distance des fentes"){
		    objet[num_obj_fentes].distance_des_fentes_en_pixels*=2.;
		    objet[num_obj_fentes].carac2*=2.;
		}
		if(command=="diviser par deux la distance des fentes"){
		    objet[num_obj_fentes].distance_des_fentes_en_pixels/=2.;
		    objet[num_obj_fentes].carac2/=2.;
		}
		if(!impossible){
		    diffr_int.fnte.ordonnees_des_fentes();
		    intensites_de_base_deja_calculees=false;
		    intensite_norme=0.;
		    command="";
		    du_nouveau_a_voir=true;
		    if(plaque_photo!=null)
			ecrit_message_important("Attendez un moment, svp");
		}
	    }
	}
	class fentes extends objectt{
	    boolean une_fente_deplacee=false;
	    fentes(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
		super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
		largeur_fente_en_pixels=carac;
		largeur_fente_en_pixels0=carac;
		nfentes=(int)carac3;
		distance_des_fentes_en_pixels=carac2;
		num_obj_fentes=num_obj;
		pt.print("ùùùùùùùùùùùùùnfentes "+nfentes+" distance_des_fentes_en_pixels "+(float)distance_des_fentes_en_pixels+" pt ");
		ordonnees_des_fentes();
	    }
	    void dessine(){
		for(int i=-2;i<3;i++){
		    toto.assigne(pt.x+i,y_banc-80.);
		    titi.assigne(pt.x+i,y_banc+80.);
		    drawlinj(toto,titi,Color.black);
		}
		for(int i=0;i<nfentes;i++){
		    double yplus=(yfente_en_pixels[i]+largeur_fente_en_pixels/2.0-y_banc)*echelle_transverse;
		    double ymoins=(yfente_en_pixels[i]-largeur_fente_en_pixels/2.0-y_banc)*echelle_transverse;
		    subject.eraserect(gTampon_appareil,(int)Math.round(ymoins+0.001)+(int)y_banc,(int)Math.round(pt.x)-2,(int)Math.round(yplus+0.001)+(int)y_banc,(int)Math.round(pt.x+2),Color.white);
		    System.out.println("i "+i+" yfente_en_pixels[i] "+yfente_en_pixels[i]+" yplus "+yplus+" ymoins "+ymoins);	
		}
	    }
	    void consequence_deplacement(int num_dep,point pt_dep){
		yfente_en_pixels[num_dep]=(pt_dep.y-y_banc)/echelle_transverse+y_banc;
		point_a_deplacer[num_dep].x=pt0.x;
		une_fente_deplacee=true;
	    }
	    void ordonnees_des_fentes(){
		double yf=pt.y-(nfentes/2)*distance_des_fentes_en_pixels;
		if(nfentes/2*2==nfentes)
		    yf+=0.5*distance_des_fentes_en_pixels;
		for(int i=0;i<nfentes;i++){
		    yfente_en_pixels[i]=yf;
		    yf+=distance_des_fentes_en_pixels;
		    System.out.println("i "+i+" yfente_en_pixels[i] "+yfente_en_pixels[i]);
		}
		nb_pt_a_deplacer=nfentes;
		for(int i=0;i<nfentes;i++){
		    point_a_deplacer[i].assigne(pt.x,(yfente_en_pixels[i]-y_banc)*echelle_transverse+y_banc);
		    point_a_deplacer[i].print(" i "+i+" echelle_transverse "+echelle_transverse+" point_a_deplacer[i] ");
		}
	    }
	}
	void intensites_de_base(){
	    for(int ptt_y=-149;ptt_y<150;ptt_y++){
		chgt_nb_lambda[ptt_y+150]=false;
		dchemin_en_lambda_prec=-10000;dchemin_en_lambda=-10000;
		ypoint[150+ptt_y]=objet[num_obj_pm].pt.y+ptt_y;
		if(Math.abs(ptt_y)<7)
		    System.out.println(" num_obj_fentes "+num_obj_fentes);
		    for(int ptt_z=ii_zmin;ptt_z<ii_zmax;ptt_z++){
			toto.assigne(Amplitude_diffraction_bis(src.pt,ptt_y,ptt_z,lambda));
			intensite=toto.longueur_carre();
			sauve_les_intensites(ptt_y,ampere.intens,150,intensite,ptt_z);
			if(ptt_z==150)
			    int_norm_monochr=intensite_norme;
		}
	    }
	}	
	point Amplitude_diffraction_bis(point cord_source,int pt_y,int pt_z,double lambd){
	    double yf,zf,phase_base=0.,delta_phase=0.;
	    //int jmax=2;
	    cucu=2*pi/lambd*cms_par_pixels;
	    int jmax=6;
	    y_du_PM=photom.pt.y+pt_y/echelle_transverse;
	    point Amplit=new point(zer);int ipro=0,j=0,k=0;
	    for(int i=0;i<fnte.nfentes;i++){
		yf=fnte.yfente_en_pixels[i]-fnte.largeur_fente_en_pixels*(1./2.+1./(2.0*jmax));
		for(j=0;j<jmax;j++){
		    yf+=fnte.largeur_fente_en_pixels/jmax;
		    titi.assigne(fnte.pt.x,yf);titi.soustrait(cord_source);
		    cici=titi.longueur();
		    tata.assigne(objet[num_obj_pm].pt.x-fnte.pt.x,y_du_PM-yf);
		    caca=tata.longueur();
		    for(ipro=-iprofondeur;ipro<=iprofondeur;ipro++){
			zf=fnte.largeur_fente_en_pixels0*(ipro-1./2.-1./(2.0*jmax));
			ne_pas_faire_d_approx=false;
			for(k=0;k<jmax;k++){
			    zf+=fnte.largeur_fente_en_pixels0/jmax;
			    aoao=zf/cici;
			    dodo=aoao*aoao;
			    distance_avant=cici*(1.+dodo/2.-dodo*dodo/8.);
			    aoao=((pt_z-150)/echelle_transverse-zf)/caca;
			    dodo=aoao*aoao;
			    distance_apres=caca*(1.+dodo/2.-dodo*dodo/8.);
			    //distance_apres=Math.sqrt(caca+Math.pow((pt_z-150)/echelle_transverse-zf,2));
			    phase_diffraction=cucu*(distance_apres+distance_avant);
			    //System.out.println(" j " +j+" k "+k+" phase_diffraction "+phase_diffraction);
			    if(i==0&&j==0&&k==0&&ipro==-iprofondeur)
				phase_diffraction0=phase_diffraction;
			    if(k!=0){
				aoao=phase_diffraction-phase_diffraction_prec;
				if(Math.abs(aoao)<0.1){
				    cococos=cocos;
				    sisisin=sisin;
				    aoao=phase_diffraction-phase_diffraction_prec;
				    cocos=cocos-aoao*sisisin-aoao*aoao*cococos/2.;
				    sisin=sisin+aoao*cococos-aoao*aoao*sisisin/2.;
				}else
				    ne_pas_faire_d_approx=true;
			    }
			    if(k==0||ne_pas_faire_d_approx){
				cocos=Math.cos(phase_diffraction-phase_diffraction0);
				sisin=Math.sin(phase_diffraction-phase_diffraction0);
			    }
			    Amplit.x +=cocos;
			    Amplit.y +=sisin;
			    phase_diffraction_prec=phase_diffraction;
			}
		    }
		}
	    }
	    if(pt_y==0&&pt_z==150)
		Amplit.print("int_norm_monochr "+int_norm_monochr+" Amplit ");
	    return Amplit;
	}
    }


    abstract class lame extends objectt{
	lame(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
	}
	void dessine(){
	    //System.out.println(" uuuuuuuuuuuuu ");
	    if(a_peu_pres_vertical){
		//System.out.println(" vvvvvv ");
		toto.assigne(pt.x,pt.y-longueur);
		titi.assigne(pt.x,pt.y+longueur*0.8);
		drawlinj(toto,titi,Color.black);
		/*
		if(nature==lame_semireflechissante){
		    toto.assigne(pt.x-1,pt.y-longueur);
		    titi.assigne(pt.x-1,pt.y+longueur*0.8);
		    drawlinj(toto,titi,Color.black);
		}
		*/
		toto.assigne(pt.x+carac,pt.y-longueur);
		titi.assigne(pt.x+carac,pt.y+longueur*0.8);
		drawlinj(toto,titi,Color.black);
		if(leger_coin){
		    //System.out.println(" wwwwwww ");
		    toto.assigne(pt.x+carac+longueur*Math.sin(ang_coin),pt.y-longueur*Math.cos(ang_coin));
		    titi.assigne(pt.x+carac-longueur*0.8*Math.sin(ang_coin),pt.y+longueur*0.8*Math.cos(ang_coin));
		    drawlinj(toto,titi,Color.black);
		    System.out.println(" ang_coin "+ang_coin);
		}
		gTampon_appareil.setColor(Color.lightGray);
		subject.paintrect(gTampon_appareil,(int)(pt.y-longueur),(int)Math.round(pt.x),(int)(pt.y+longueur*0.8),(int)Math.round(pt.x+carac));
	    }else if(a_peu_pres_horizontal){
		toto.assigne(pt.x-longueur,pt.y);
		titi.assigne(pt.x+longueur,pt.y);
		drawlinj(toto,titi,Color.black);
		if(nature==lame_semireflechissante){
		    toto.assigne(pt.x-longueur,pt.y-1);
		    titi.assigne(pt.x+longueur,pt.y-1);
		    drawlinj(toto,titi,Color.black);
		}
		gTampon_appareil.setColor(Color.lightGray);
		subject.paintrect(gTampon_appareil,(int)(pt.y-Math.abs(carac)),(int)Math.round(pt.x-longueur),(int)(pt.y),(int)Math.round(pt.x+longueur));
		if(i_demarre!=-1){
		    pt.print(" pt ");
		    //objet[242]=null;
		}

		//subject.paintrect(gTampon_appareil,y_banc-(int)longueur,(int)Math.round(pt.x),y_banc+(int)longueur,(int)Math.round(pt.x+carac));
	    }else{
		cocos=Math.cos(angle_vs_normale_au_banc);
		sisin=Math.sin(angle_vs_normale_au_banc);
		toto.assigne(pt.x+longueur*1.5*cocos+carac/cocos,pt.y-longueur*1.5*sisin);
		titi.assigne(pt.x-longueur*cocos+carac/cocos,pt.y+longueur*sisin);
		drawlinj(toto,titi,Color.black);
		toto.assigne(pt.x+longueur*1.5*cocos,pt.y-longueur*1.5*sisin);
		titi.assigne(pt.x-longueur*cocos,pt.y+longueur*sisin);
		drawlinj(toto,titi,Color.black);
		if(nature==lame_semireflechissante){
		    toto.assigne(pt.x-1+longueur*1.5*cocos,pt.y-longueur*1.5*sisin);
		    titi.assigne(pt.x-1-longueur*cocos,pt.y+longueur*sisin);
		    drawlinj(toto,titi,Color.black);
		}
		gTampon_appareil.setColor(Color.lightGray);
	    }
	}
	void consequence_deplacement(int num_dep,point pt_dep){
	    if(num_dep==0){
		if(a_peu_pres_vertical)
		carac-=(pt_dep.x-pt.x);
		if(!a_peu_pres_horizontal)
		    pt.x=pt_dep.x;
		else
		    pt.y=pt_dep.y;
	    }else if (num_dep==1)
		if(a_peu_pres_vertical)
		    carac=pt_dep.x-pt.x;
		else
		    carac=(pt_dep.y-pt.y);
	    point_a_deplacer[0].assigne(pt);
	    if(a_peu_pres_vertical){
		point_a_deplacer[1].assigne((double)pt.x+carac,(double)pt.y);
		x_sur_le_plan_aval=pt.x+carac;
	    }else{
		point_a_deplacer[1].assigne((double)pt.x,(double)pt.y+carac);
		System.out.println("carac "+(float)carac);
		y_sur_le_plan_aval=pt.y;
	    }
	}
	void modifie_l_echemlle(){
	    if(a_peu_pres_horizontal&&interferon==lame_par_reflex){
		System.out.println("echelle_transverse "+(float)echelle_transverse+" carac "+(float)carac);
		echelle_transverse=echelle_transverse0/Math.abs(carac/lame_par_reflex.epaisseur_reference);
		System.out.println("echelle_transverse "+(float)echelle_transverse);
	    }
	}
    }
    class lame_semireflechissante extends lame{
	lame_semireflechissante(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
	    indice=carac2;
	    x_sur_le_plan_aval=ptx+carac;
	    
	    if(michelsonne){
		nb_pt_a_deplacer=0;
		//point_a_deplacer[0].assigne((double)ptx,(double)y_banc);
		//point_a_deplacer[1].assigne((double)ptx+carac,(double)y_banc);
	    }else{
		nb_pt_a_deplacer=2;
		    point_a_deplacer[0].assigne((double)pt.x,(double)pt.y);
		if(Math.abs(angle_vs_normale_au_banc)<0.0000001){
		    point_a_deplacer[1].assigne((double)pt.x+carac,(double)pt.y);
		}else{
		    point_a_deplacer[1].assigne((double)ptx,(double)pt.y+carac);
		}
	    }
	}
    }
    class lame_a_faces_paralleles extends lame{
	lame_a_faces_paralleles(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
	    indice=carac2;
	    x_sur_le_plan_aval=ptx+carac;
	    if(michelsonne){
		nb_pt_a_deplacer=0;
		//point_a_deplacer[0].assigne((double)ptx,(double)y_banc);
		//point_a_deplacer[1].assigne((double)ptx+carac,(double)y_banc);
	    }else{
		nb_pt_a_deplacer=2;
		point_a_deplacer[0].assigne((double)ptx,(double)y_banc);
		point_a_deplacer[1].assigne((double)ptx+carac,(double)y_banc);
	    }
	}
    }
    
    class bi_miroirs_paralleles extends objectt{
	double carac0;	double angle_vs_normale_au_banc=0.;
	bi_miroirs_paralleles(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
	    x_sur_le_plan_aval=ptx+carac;
	    carac0=carac1;
	    nb_pt_a_deplacer=2;
	    point_a_deplacer[0].assigne((double)ptx,(double)y_banc);
	    point_a_deplacer[1].assigne((double)ptx+carac,(double)y_banc);
	}
	void dessine(){
	    x_sur_le_plan_aval=pt.x+carac;
	    point_a_deplacer[0].assigne((double)pt.x,(double)y_banc);
	    point_a_deplacer[1].assigne((double)pt.x+carac,(double)y_banc);
	    toto.assigne(pt.x+1,y_banc-longueur);
	    titi.assigne(pt.x+1,y_banc+longueur*0.8);
	    drawlinj(toto,titi,Color.black);
	    toto.assigne(pt.x-1+carac,y_banc-longueur);
	    titi.assigne(pt.x-1+carac,y_banc+longueur*0.8);
	    drawlinj(toto,titi,Color.black);
	    gTampon_appareil.setColor(Color.blue);
	    if(leger_coin){
		System.out.println(" wwwwwww ");
		toto.assigne(pt.x-1+carac+longueur*Math.sin(ang_coin),pt.y-longueur*Math.cos(ang_coin));
		titi.assigne(pt.x-1+carac-longueur*0.8*Math.sin(ang_coin),pt.y+longueur*0.8*Math.cos(ang_coin));
		drawlinj(toto,titi,Color.blue);
	    }
	    subject.paintrect(gTampon_appareil,(int)y_banc-(int)longueur,(int)Math.round(pt.x),(int)(y_banc+(int)longueur*0.8),(int)Math.round(pt.x+carac));
	}
	void consequence_deplacement(int num_dep,point pt_dep){
	    if(num_dep==0){
		carac-=(pt_dep.x-pt.x);
		pt.x=pt_dep.x;
	    }else if (num_dep==1){
		carac=pt_dep.x-pt.x;
	    }
	    point_a_deplacer[0].assigne(pt);
	    point_a_deplacer[1].assigne((double)pt.x+carac,(double)pt.y);
	    x_sur_le_plan_aval=pt.x+carac;
	}
    }
    class PM extends objectt{
	PM(double ptx,double pty,double angle_vs_normale_au_banc1,int longueur1,int nature1,double carac1,double carac21,double carac31,int num_obj1){
	    super(ptx,pty,angle_vs_normale_au_banc1,longueur1,nature1,carac1,carac21,carac31,num_obj1);
	    num_obj_pm=num_obj;
	    nb_pt_a_deplacer=0;
	    //point_a_deplacer[0].assigne((double)ptx,(double)y_banc);
	}
	void efface(int y){
	    subject.eraserect(gTampon_appareil,y-10,(int)Math.round(pt.x),y+10,(int)Math.round(pt.x)+20,Color.white);
	}
	void dessine(){
	}
	void consequence_deplacement(int num_dep,point pt_dep){
	    pt.assigne(pt_dep);
	    point_a_deplacer[0].assigne(pt);
	}
    }

    class MouseMotion extends MouseMotionAdapter{
	ensemble_optique subj;
	public MouseMotion(ensemble_optique a){
	    subj=a;
	}
	public void mouseMoved(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();draguee=false;
	}
	public void mouseDragged(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();draguee=true;
	    //System.out.println("draguee dans Mousemove "+draguee);
	    traite_click();
	}
    }
    
    class MouseStatic extends MouseAdapter{
	ensemble_optique subj;
	public MouseStatic(ensemble_optique a){
	    subj=a;
	}
	public void mouseClicked(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("kammer[icylindre].nb_el_ens "+kammer[icylindre].nb_el_ens);
	    //System.out.println("icylindre "+icylindre);
	    //for( int iq=0;iq<kammer[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;relachee=false;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    relachee=true;pressee=false;draguee=false;
	    System.out.println("relachee "+relachee);
	    traite_click();
	}
    }
    public void	traite_click(){
	System.out.println("entree traite_click cliquee "+cliquee);
	    if(grande_taille){
		ppmouseh/=2;
		ppmousev/=2;
	    }
	int xi=ppmouseh;boolean dg;
	int yi=ppmousev;
	int ijj=0;
	while(occupied&&ijj<200){
	    if(ijj/20*20==ijj)
		System.out.println(" dans traite_click() ijj "+ijj);
	     ijj++;
	 }
	//System.out.println("xi "+xi+" yi "+yi);
	if(cliquee){
	    //seulement_pour_montrer_rayons=false;
	    Date maintenant=new Date();
	    subject.temps_initial_en_sec=subject.temps_en_secondes(maintenant);
	    if(!(ppmouseh==ppmouseh_prec&&ppmousev==ppmousev_prec)){
		ppmouseh_prec=ppmouseh;ppmousev_prec=ppmousev;
		System.out.println(" ppmouseh_prec "+ppmouseh_prec+" ppmousev_prec "+ppmousev_prec);	
	    }else
		cliquee=false;
	    //cliquee=false;
	}
	if(command!=""||deplacer_un_objet){
		command_prec=command;
		dg=gerelesmenus_avec_souris();
		if(fin_gerelesmenus_avec_souris)
		    command="";
	}
	if(cliquee&&(ppmousev > top2&&ppmousev < bot2&&ppmouseh > left2&&ppmouseh < right2)){
	    lumiere_composee=false;
	    lambda=min2+(ppmouseh-left2)*(max2-min2)/(right2-left2);
	    indice_lamb=(int)((lambda-min2)*100./(max2-min2));
	    if(ppmouseh-left2<100){
		couleur_de_la_lumiere.assigne(farbe_bei_laenge[ppmouseh-left2]);
		couleur_lumiere=couleur_de_la_lumiere.col;
	    }
	    //seulement_pour_montrer_rayons=true;
	    couleur_de_la_lumiere.print(" couleur_de_la_lumiere ");
	    System.out.println("lambda en microns "+lambda*1e4);
	    intensites_de_base_deja_calculees=false;
	    intensite_norme=0.;
	    if(!diffraction){
		if(michelsonne)
		    interf_mich.matching_et_intensites_deja_calcules=false;
		int ptt_y=0,ptt_z=0;
		if(!michelson_deregle&&!lumiere_composee&&!source_etendue){
		    interferon.dchemin_en_lambda_prec=-10000;
		    interferon.dchemin_en_lambda=-10000;
		    for(ptt_y=-149;ptt_y<150;ptt_y++)
			interferon.chgt_nb_lambda[ptt_y+150]=false;		    
		    for(ptt_y=-149;ptt_y<149;ptt_y++){
			if(ptt_y/10*10==ptt_y)
			    System.out.println(" fab_lame_mich "+fab_lame_mich+" interferon "+interferon);
			if(fab_lame_mich==interferon){
			    cece=-fab_lame_mich.diff_de_ch_opt[150+ptt_y][150]/lambda;
			    interferon.calcule_nb_lambda_diff(ptt_y,cece);
			    for(ptt_z=interferon.ii_zmin;ptt_z<interferon.ii_zmax;ptt_z++){

				fab_lame_mich.ampere.intens[150+ptt_y][ptt_z]=(float)fab_lame_mich.intensite_en_fonction_de_diff_chemins(fab_lame_mich.diff_de_ch_opt[150+ptt_y][ptt_z],lambda);
				if(ptt_z==150&&fab_lame_mich.ampere.intens[150+ptt_y][ptt_z]>intensite_norme)
				    intensite_norme=fab_lame_mich.ampere.intens[150+ptt_y][ptt_z];
				if(ptt_y/10*10==ptt_y&&ptt_z==150)
				    System.out.println("ùùùùùùùù ptt_y "+ptt_y+" interf_mich.ampere.intens[150+ptt_y][150] "+(float)fab_lame_mich.ampere.intens[150+ptt_y][150]+" intensite_norme "+(float)intensite_norme);
			    }
			}
		    }
		    if(michelsonne)
			if(!source_etendue){
			    int_norm_monochr=intensite_norme;
			    System.out.println(" ùùùùùùùùùùù int_norm_monochr "+(float)int_norm_monochr);
			}
			else
			    int_norm_etendue_monochr=intensite_norme;
		}
	    }
	    System.out.println(" on sort de traite_click ");
	    du_nouveau_a_voir=true;
	}
	cliquee=false;
	System.out.println("sortie traite_click ");
    }
    public void actionPerformed(ActionEvent e){
	System.out.println("e "+e);
	int ijj=0;
	while(occupied&&ijj<100){
	    ijj++;
	    System.out.println(" dans actionPerformed(ActionEvent e) ijj "+ijj);
	}
	command=e.getActionCommand();
	System.out.println("command "+command);
	if(command!=""){
	    Date right_noww=new Date();
	    temps_action=subject.temps_en_secondes(right_noww);
	    subject.temps_initial_en_sec=temps_action;
	    System.out.println(" command "+command+" command_prec "+command_prec+" temps_action "+temps_action+" temps_action_prec "+temps_action_prec);
	    if(command==command_prec&&temps_action<temps_action_prec+2){
		System.out.println(" command "+command+" command_prec "+command_prec);
		command="";
	    }
	    if(command!=""){
		command_prec=command;
		temps_action_prec=temps_action;
		if (command=="Continuer"){
		    du_nouveau_a_voir=true;
		    command="";
		}
		if (command=="Revenir a la page principale"||command=="Sortir du programme"||command=="Revenir a la page initiale avec infos"){
		    le_virer=true;
		    subject.toutdebut=command=="Revenir a la page initiale avec infos";
		    if(command=="Sortir du programme"){
			subject.on_s_en_va();
			subject.dispose();
			System.exit(0);
		    }
		}else if (vibration)
		    interferon.stop_vibration();
		else if(command!="")
		   traite_commande();
	    }
	}
    }
    void annule_l_autre_ensemble(){
	if(subject.n_ensembles==2){
	    if(subject.ensemble[1-i_ens].interferon!=null)
		//subject.ensemble[1-i_ens].setVisible(false);
		if(subject.ensemble[1-i_ens].sp_couleur!=null){
		    subject.ensemble[1-i_ens].sp_couleur.dispose();
		    subject.ensemble[1-i_ens].sp_couleur=null;
		}
	    if(subject.ensemble[1-i_ens].plaque_photo!=null){
		subject.ensemble[1-i_ens].plaque_photo.dispose();
		subject.ensemble[1-i_ens].plaque_photo=null;
	    }
	    subject.ensemble[1-i_ens].dispose();
	    subject.ensemble[1-i_ens]=null;
	    System.out.println("1-i_ens "+(1-i_ens)+" subject.ensemble[1-i_ens] "+subject.ensemble[1-i_ens]);
	}
    }

    public boolean gerelesmenus_avec_souris(){ 
	if(deplacer_un_objet){
	    System.out.println("ùùù gerelesmenus_avec_souris draguee"+draguee+" pressee "+pressee+" relachee "+relachee );
	    fin_deplacer_un_objet=false;
	    if(draguee||trouve_deplacement)
		glisser_vib();
	    if(pressee){
		System.out.println("avant glisser ");
		glisser_vib();
	    }
	    if(relachee){
		glisser_vib();
		relachee=false;
		command="";
	    }
	}
	return true;
    } 
    void ecrire_bandeau(Graphics gg){
	subject.eraserect(gg,bandeau_y-20,bandeau_x,bandeau_y,bandeau_x+1000,Color.white);
	gg.setColor(Color.blue);
	gg.setFont(subject.times_gras_14);
	gg.drawString(comment,bandeau_x,bandeau_y);
	gg.setFont(subject.times14);
    }
    public void glisser_vib(){
	int iii;
	pt_souris.assigne((double)ppmouseh,(double)ppmousev);
	//if(grande_taille)
	//pt_souris.multiplie_cst(0.5);
	System.out.println("glisser_vib trouve_deplacement"+trouve_deplacement+"pressee"+pressee);
	if(pressee){
	    if(!trouve_deplacement){
		for(int iq=0;iq<nb_objets;iq++){
		    for(int idep=0;idep<objet[iq].nb_pt_a_deplacer;idep++){
			System.out.println("iq cherche "+iq+" idep "+idep);
			dpt_souris.assigne_soustrait(objet[iq].point_a_deplacer[idep],pt_souris);
			if(Math.abs(dpt_souris.x)<=3&&Math.abs(dpt_souris.y)<=3){
			    comment="";
			    trouve_deplacement=true;//calcul_equip=false;
			    num_dep=idep;
			    num_obj_dep=iq;
			    System.out.println(" *****************deplacement initial,iq "+iq+" idep "+idep);
			    break;
			}
		    }
		    if(trouve_deplacement)
			break; 
		}
	    }
	}
	if(trouve_deplacement){
	    if(relachee){
		System.out.println("&&&&&&&&&&&&&&&&&& num_obj_dep"+num_obj_dep+" num_dep"+num_dep);
		trouve_deplacement=false;
		command="";fin_gerelesmenus_avec_souris=true;
		deplacer_un_objet=false;
		points_rouges_allumes=false;
		fin_deplacer_un_objet=true;		
		objet[num_obj_dep].point_a_deplacer[num_dep].assigne_soustrait(pt_souris,dpt_souris);
		objet[num_obj_dep].consequence_deplacement(num_dep,objet[num_obj_dep].point_a_deplacer[num_dep]);
		if(interferon==lame_par_reflex&&objet[num_obj_dep]==lame_semi)
		    lame_semi.modifie_l_echemlle();
		if(michelsonne||diffraction||perot_lame){
		    int_norm_lum_bl=0;
		    int_norm_etendue_monochr=0;
		    int_norm_etendue_lum_bl=0;
		    intensites_de_base_deja_calculees=false;
		    if(michelsonne){
			interf_mich.resultats_f_d_angle_deja_calcules=false;
			interf_mich.matching_et_intensites_deja_calcules=false;
		    } 
		    du_nouveau_a_voir=true;
		}
		du_nouveau_a_voir=true;
	    }else if(draguee){
		System.out.println("draguee num_obj_dep "+num_obj_dep+" num_dep "+num_dep);
		objet[num_obj_dep].point_a_deplacer[num_dep].assigne_soustrait(pt_souris,dpt_souris);
		objet[num_obj_dep].consequence_deplacement(num_dep,objet[num_obj_dep].point_a_deplacer[num_dep]);
		if(!interferometrie&&num_obj_dep==1)
		    optic_geo.definit_faisceau_incident();
		du_nouveau_a_voir=true;
	    }
	}
    }
    public void barre_des_menus(){
	System.out.println("i_demarre  "+i_demarre);
	mb1[i_ens]=new MenuBar();
	
	Menu operations_sur_elements= new Menu("modifier le dispositif");
	if(perot_lame){
	    for(int i=0;i<5;i++){
		itep_coin.add(interferon.angle_coin_item[i]);
		interferon.angle_coin_item[i].addActionListener(this);
	    }
	    operations_sur_elements.add(itep_coin);
	}
	if(michelsonne){
	    for(int i=0;i<5;i++){
		itep_miroir_gauche_deregle.add(interf_mich.angle_miroir_gauche[i]);
		interf_mich.angle_miroir_gauche[i].addActionListener(this);
	    }
	}
	if(michelsonne){
	    Menu itep_lame=new Menu("lame");
	    MenuItem itep_perp=new MenuItem("miroirs perpendiculaires");
	    itep_lame.add(itep_perp);
	    itep_perp.addActionListener(this);
	    Menu itep_non_perp=new Menu("miroirs non perpendiculaires");
	    for(int i=0;i<5;i++){
		itep_non_perp.add(interf_mich.angle_des_2_miroirs_item[i]);
		interf_mich.angle_des_2_miroirs_item[i].addActionListener(this);
	    }
	    itep_lame.add(itep_non_perp);

	    for(int i=0;i<5;i++){
		itep_miroir_gauche_deregle.add(interf_mich.angle_miroir_gauche[i]);
		interf_mich.angle_miroir_gauche[i].addActionListener(this);
	    }
	    operations_sur_elements.add(itep_lame);
	}else if(perot_lame){
	    MenuItem itep_lame_d_air=new MenuItem("lame");
	    operations_sur_elements.add(itep_lame_d_air);
	    itep_lame_d_air.addActionListener(this);
	}
	if(!lame_reflexion&&!lame_transmission){
	    MenuItem itep_move=new MenuItem("deplacer un element");
	    operations_sur_elements.add(itep_move);
	    itep_move.addActionListener(this);
	}
	if(michelsonne){
	    MenuItem itep_egal=new MenuItem("Chemins optiques egaux");
	    operations_sur_elements.add(itep_egal);
	    itep_egal.addActionListener(this);
	}
	if(diffraction){
	    MenuItem itepp=new MenuItem("ajouter une fente");
	    operations_sur_elements.add(itepp);
	    itepp.addActionListener(this);
	    MenuItem itepep=new MenuItem("multiplier par deux l'epaisseur des fentes");
	    operations_sur_elements.add(itepep);
	    itepep.addActionListener(this);
	    MenuItem itepepdiv=new MenuItem("diviser par deux l'epaisseur des fentes");
	    operations_sur_elements.add(itepepdiv);
	    itepepdiv.addActionListener(this);


	    profondeur=new Menu("Modifier la profondeur des fentes.");
	    for(int i=0;i<3;i++){
		profondeur.add(diffr_int.profondeur_de[i]);
		diffr_int.profondeur_de[i].addActionListener(this);
		operations_sur_elements.add(profondeur);
	    }
	}
	if(interferometrie){
	    Menu diff_source_etendue=new Menu("source etendue");
	    for(int ik=0;ik<5;ik++){
		diff_source_etendue.add(interferon.s_etendue[ik]);
		interferon.s_etendue[ik].addActionListener(this);
	    }
	    operations_sur_elements.add(diff_source_etendue);
	    if(source_etendue){
		MenuItem diff_source_ponctuelle=new MenuItem("source ponctuelle");
		operations_sur_elements.add(diff_source_ponctuelle);
		diff_source_ponctuelle.addActionListener(this);
	    }
	}
	if(perot_lame&!lame_transmission){
	Menu fact_reflexion=new Menu("Pouvoir reflecteur en amplitude ");
	for(int i=0;i<=9;i++){
		fact_reflexion.add(facteur_refl[i]);
		facteur_refl[i].addActionListener(this);
	}
	    operations_sur_elements.add(fact_reflexion);
	}
	if(perot_lame||i_demarre==microscope||michelsonne){
	    if(i_demarre==perot_fabry||michelsonne){
		rapprocher=new Menu("Rapprocher le miroir de gauche de");
		reculer=new Menu("Reculer le miroir de gauche de");
	    }else if(lame_reflexion||lame_transmission){
		rapprocher=new Menu("Ajouter a l'epaisseur de la lame");
		reculer=new Menu("Retrancher a l'epaisseur de la lame");
	    }else if(i_demarre==microscope){
		rapprocher=new Menu("Deplacer la source vers la droite");
		reculer=new Menu("Deplacer la source vers la gauche");
	    }
	    for(int i=0;i<=9;i++)
		if(!(i>5&&lame_reflexion)){
		    rapprocher.add(rapprocher_de[i]);
		    rapprocher_de[i].addActionListener(this);
		}	
	    operations_sur_elements.add(rapprocher);
	    for(int i=0;i<=9;i++)
		if(!(i>5&&lame_reflexion)){
		reculer.add(reculer_de[i]);
		reculer_de[i].addActionListener(this);
	    }	
	    operations_sur_elements.add(reculer);
	}
	if(!interferometrie){
	    if(i_demarre!=70&&i_demarre!=71){
		operations_sur_elements.add(optic_geo.iteoeil);
		optic_geo.iteoeil.addActionListener(this);
	    }
	    operations_sur_elements.add(optic_geo.faisc_parallele);
	    optic_geo.faisc_parallele.addActionListener(this);
	    if(i_demarre==60){		
		optic_geo.itep_vitre.addActionListener(this);
		operations_sur_elements.add(optic_geo.itep_vitre);
	    }
	    if(i_demarre==60||i_demarre==61){		
		optic_geo.itep_inversion_indices.addActionListener(this);
		operations_sur_elements.add(optic_geo.itep_inversion_indices);
	    }
	}else{
	    if(michelsonne){
		operations_sur_elements.add(itep_miroir_gauche_deregle);
		itep_miroir_gauche_deregle.addActionListener(this);
		for(int i=0;i<5;i++){
		    itep_miroir_bas_deregle.add(interf_mich.angle_miroir_bas[i]);
		    interf_mich.angle_miroir_bas[i].addActionListener(this);
		}
		operations_sur_elements.add(itep_miroir_bas_deregle);
		itep_miroir_bas_deregle.addActionListener(this);
		itep_coin.addActionListener(this);
	    }
	    if(!diffraction){
		operations_sur_elements.add(itep_mettre_lentille_finale);
		itep_mettre_lentille_finale.addActionListener(this);
	    }
	    operations_sur_elements.add(itep_size_plus);
	    itep_size_plus.addActionListener(this);
	    operations_sur_elements.add(itep_size_moins);
	    itep_size_moins.addActionListener(this);
	    
	    item_spectre_de_raies_3.addActionListener(this);
	    item_spectre_de_raies_2.addActionListener(this);
	    item_spectre_de_raies_1.addActionListener(this);
	    item_spectre_continu.addActionListener(this);
	    operations_sur_elements.add(itep_composee);
	    MenuItem itep_monoch=new MenuItem("Source de lumiere monochromatique");
	    itep_monoch.addActionListener(this);
	    operations_sur_elements.add(itep_monoch);
	}
	mb1[i_ens].add(operations_sur_elements);
	Menu actions= new Menu("Actions");
	if(i_demarre!=-1&&(michelsonne||perot_fabryyyy)){
	    for(int i=0;i<10;i++){
		menu_vib.add(vibration_de[i]);
		vibration_de[i].addActionListener(this);
	    }
	    actions.add(menu_vib);
	}
	if(interferon!=lame_par_reflex&&!michelson_deregle&&faces_perot_lame_non_paralleles==false){
	    actions.add(itep_couleur);
	    itep_couleur.addActionListener(this);
	}
	if(i_demarre==-1)
	    itep_couleur.setEnabled(false);
	if(interferometrie){
	    actions.add(itep_agrandir);	
	    itep_agrandir.addActionListener(this);
	}    
	if(i_demarre!=-1){
	    MenuItem iteb1=new MenuItem("Revenir a la page principale");
	    actions.add(iteb1);
	    iteb1.addActionListener(this);
	    MenuItem iteb11=new MenuItem("Revenir a la page initiale avec infos");
	    actions.add(iteb11);
	    iteb11.addActionListener(this);
	    MenuItem iteb12=new MenuItem("Sortir du programme");
	    actions.add(iteb12);iteb12.addActionListener(this);
	}
	MenuItem iteb_continue=new MenuItem("Continuer");
	actions.add(iteb_continue);iteb_continue.addActionListener(this);
	mb1[i_ens].add(actions);
	if(interferometrie){
	    Menu expliquer= new Menu("Explications");
	    MenuItem itep_explique=new MenuItem("Explications generales");
	    itep_explique.addActionListener(this);
	    expliquer.add(itep_explique);
	    MenuItem itep_explique1=new MenuItem("Explications sur lumiere composee");
	    itep_explique1.addActionListener(this);
	    expliquer.add(itep_explique1);
	    if(michelsonne||perot_fabryyyy){
		MenuItem itep_explique2=new MenuItem("Explications sur vibrations");
		itep_explique2.addActionListener(this);
		expliquer.add(itep_explique2);
		if(i_demarre==-1)
		    itep_explique2.setEnabled(false);
	    }
	    if(i_demarre==-1){
		itep_explique.setEnabled(false);
		itep_explique1.setEnabled(false);
	    }
	    mb1[i_ens].add(expliquer);
	}
	/*
else{
	  MenuItem itep_explique=new MenuItem("Explications optique geometrique");
	    itep_explique.addActionListener(this);
	    expliquer.add(itep_explique);
	}
	*/
	if(i_demarre>=0&&i_demarre<=6&&!vibration){
	    Menu ajouter= new Menu("Autre appareil");
	    for (int i=0;i<9;i++){
		if(i_demarre<=5&&i<=5||i_demarre>5&&i>5){
		    itab[i]=new MenuItem(subject.titre[i]);
		    ajouter.add(itab[i]);
		    itab[i].addActionListener(this);
		}
	    }
	    mb1[i_ens].add(ajouter);
	}
	setMenuBar(	mb1[i_ens]);
	//pack();setVisible(true);
    }
    void drawline_plot(Graphics g,double x1,double y1,double x2,double y2,Color couleur){
	subject.drawline_couleur(g,(int)Math.round(x1),(int)Math.round(y1),(int)Math.round(x2),(int)Math.round(y2),couleur);
    }
    void drawling(double x1,double y1,double x2,double y2,Color couleur){
	subject.drawline_couleur(gTampon_appareil,(int)Math.round(x1),(int)Math.round(y1),(int)Math.round(x2),(int)Math.round(y2),couleur);
    }
    void drawlinj(point p1,point p2,Color couleur){
	subject.drawline_couleur(gTampon_appareil,(int)Math.round(p1.x),(int)Math.round(p1.y),(int)Math.round(p2.x),(int)Math.round(p2.y),couleur);
	//subject.drawline_couleur(grp_c,(int)Math.round(p1.x),(int)Math.round(p1.y),(int)Math.round(p2.x),(int)Math.round(p2.y),couleur);
    }
    void drawlinh(point p1,point p2,Color couleur){

	if(dessine_parcours){
	    subject.drawline_couleur(gTampon_appareil,(int)Math.round(p1.x),(int)Math.round(p1.y),(int)Math.round(p2.x),(int)Math.round(p2.y),couleur);
	}
    }
    void paintcirclh(int x1,int y1,int rc,Color couleur){
	subject.paintcircle_couleur(gTampon_appareil,(int)Math.round(x1),(int)Math.round(y1),rc,couleur);
    }
	//du_nouveau_a_voir=false;
     public void traite_commande(){
	 System.out.println(" dans traite_commande"+command);
	 nb_commande++;
	 //if(command!="")
	 //   seulement_pour_montrer_rayons=false;
	 //else
	 if(fab_lame_mich!=null)
	     if(vibration){
		 expliquer_vibrations=false;
		 interferon.stop_vibration();
	     }
	 titre_fenetre=command;
	 if(interferometrie){
	     if(command=="Agrandir cette fenetre d'un facteur 2"||command=="Re_Agrandir cette fenetre d'un facteur 2"){
		 grande_taille=true;
		 itep_agrandir=null;
		 itep_agrandir=new MenuItem("Revenir à la taille initiale de cette fenetre");
		 setSize((right_ens_cyl-left_ens_cyl)*2,2*(bottom_ens_cyl-top_ens_cyl));
		 setLocation(left_ens_cyl,top_ens_cyl);
		 //dessine_l_image_de_l_appareil();
		 barre_des_menus();
		 du_nouveau_a_voir=true;
		 if(command=="Agrandir cette fenetre d'un facteur 2")
		     command="Re_Agrandir cette fenetre d'un facteur 2";
		 else
		     command="";
	     }
	     if(command=="Revenir à la taille initiale de cette fenetre"){
		 setSize(right_ens_cyl-left_ens_cyl,bottom_ens_cyl-top_ens_cyl);
		 setLocation(left_ens_cyl,top_ens_cyl+i_ens*350);
		 grande_taille=false;
		 subject.retour_taille_normale=true;
		 itep_agrandir=null;
		 itep_agrandir=new MenuItem("Agrandir cette fenetre d'un facteur 2");
		 if(i_demarre==-1){
		       subject.eraserect(subject.gr,0,0,1000,2000,Color.white);
		     command="";
		 }
		     //		 }else{
		     barre_des_menus();
		     du_nouveau_a_voir=true;
		     subject.ensemble[1-i_ens].du_nouveau_a_voir=true;
		     //}
		 command="";
	     }
	     if(command=="Explications generales"||command=="Explications sur lumiere composee"||command=="Explications sur vibrations"){
		 toto_string="";
		 if(!(command=="Explications generales"))
		     if(explique_lumiere_composee&&!vibration||command=="Explications sur lumiere composee")
			 toto_string="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_lumiere_blanche.jpg";
		     else if(expliquer_vibrations||vibration||command=="Explications sur vibrations"){
			 if(vibration){
			     interferon.stop_vibration();
			 }else
			     toto_string="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_vibrations.jpg";
		     }
		 if(!vibration)
		     interferon.explique(toto_string);
		 command="";
	     }
	 }else if(command=="Explications optique geometrique"){
		 va_chercher_et_affiche_explications("C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/labo_optique_optique_geometrique.jpg");
	     command="";
	 }
	 if(command=="deplacer un element"){
	     points_rouges_allumes=true;
	     deplacer_un_objet=true;
	     du_nouveau_a_voir=true;
	     System.out.println(" dans traite_commande() deplacer_un_objet"+deplacer_un_objet);
	     if(michelsonne){
		 interf_mich.revenir_aux_conditions_de_depart();
	     }		
	     command="";
	 }
	 if(command=="inverser les zones verte et blanche"){
	     double ijk=objet[1].indice_gauche;
	     objet[1].indice_gauche=objet[1].indice_droite;
	     objet[1].indice_droite=ijk;
	     ijk=objet[1].carac2;
	     objet[1].carac2=objet[1].carac3;
	     objet[1].carac3=ijk;
	     objet[1].inversion_d_indice=!objet[1].inversion_d_indice;
	     du_nouveau_a_voir=true;
	     System.out.println(" dans traite_commande() deplacer_un_objet"+deplacer_un_objet);
	     command="";
	 }
	 for (int i=0;i<11;i++){
	     if(command==subject.titre[i]){
		 subject.creation_d_un_ensemble_venue_d_un_ensemble=true;
		 subject.ensemble_d_ou_vient_l_ordre_de_creer=i_ens;
		 //annule_l_autre_ensemble();
		 subject.n_ensembles=2;
		 subject.creation_d_un_ou_deux_ensembles(1-i_ens,i);
		 command="";
		 occupied=false;
		 break;
	     }
	 }
	 if(optic_geo!=null)
	     optic_geo.traite_comm_opt_geo();
	 for (int i=0;i<10;i++){
	     if(command==rapprocher_de_[i]||command==reculer_de_[i]){
		 double dist_rappr=-distance_avant_arriere[i];
		 if(interferometrie&&!lame_transmission&&!lame_reflexion)
		     dist_rappr/=cms_par_pixels;
		 if(command==rapprocher_de_[i])
		     dist_rappr=-dist_rappr;
		 if(michelsonne){
		     miroir_gauche.pt.x+=dist_rappr;
		 }else if(perot_fabryyyy){
		     fbr_prt.bi_mirr.pt.x+=dist_rappr;
		     fbr_prt.bi_mirr.carac-=dist_rappr;
		 }else if(lame_transmission){
		     if(lame_semi.carac+dist_rappr>=1)
			 lame_semi.carac+=dist_rappr;
		     else{
			 comment="Epaisseur trop petite, operation non faite";
			 ecrire_bandeau(grp_c);
			 break;
		     }
		 }else if(lame_reflexion){
		     System.out.println("lame_semi.carac "+lame_semi.carac+"dist_rappr "+dist_rappr);
		     if(lame_semi.carac-dist_rappr<=-1&&lame_semi.carac-dist_rappr>-10.){
			 lame_semi.carac-=dist_rappr;
			 System.out.println(" eeeeeeeeeeeeee lame_semi.carac "+lame_semi.carac);
		     }else{
			 if(lame_semi.carac-dist_rappr<=-10.)
			     comment="Epaisseur trop grande, operation non faite";
			 else
			     comment="Epaisseur trop petite, operation non faite";
			 ecrire_bandeau(grp_c);
			 break;
		     }
		 }	 
		 else if(i_demarre==microscope)
		     src.pt.x+=dist_rappr;
		 int_norm_monochr=0.;
		 int_norm_lum_bl=0;
		 int_norm_etendue_monochr=0;
		 int_norm_etendue_lum_bl=0;
		 intensites_de_base_deja_calculees=false;
		 if(michelsonne){
		     interf_mich.matching_et_intensites_deja_calcules=false; 
		     interf_mich.resultats_f_d_angle_deja_calcules=false;
		 }
		 du_nouveau_a_voir=true;
		 command="";
		 break;
	     }
	 }
	 if(michelsonne||perot_lame){
	     for (int i=0;i<10;i++)
		 if(command==vibration_de_[i]){
		     if(plaque_photo!=null)
			 subject.eraserect(plaque_photo.grp_repr,0,0,400,400,Color.white);
		     explique_lumiere_composee=false;expliquer_vibrations=true;
		     ampl_vib=distance_avant_arriere[i];
		     if(michelsonne){
			 interf_mich.revenir_aux_conditions_de_depart();
			 interf_mich.resultats_f_d_angle_deja_calcules=false;
		     }
		     vibration=true;
		     source_etendue=false;
		     barre_des_menus();
		     n_vibrations=0;
		     intensites_de_base_deja_calculees=false; 
		     du_nouveau_a_voir=true;
		     if((michelsonne||perot_lame)&&lentle_2==null)
			 fab_lame_mich.mettre_ou_enlever_la_lentille_finale(true);
		     //appelle_comm();
		     break;
		 }
	 }
	 if(interferometrie)
	     interferon.traite_command();
     }

    point_coeff rayon_incident_coefficient_refracte(int jq,point_coeff pc_in){
	if(objet[jq].nature==le_dioptre_plan||objet[jq].nature==le_dioptre_spherique){
	    if(objet[jq].nature==le_dioptre_plan){
		y_sur_lobjet=pc_in.pt.y+pc_in.coeff*(objet[jq].pt.x -pc_in.pt.x);
		pt_end.assigne(objet[jq].pt.x,y_sur_lobjet);
		tata.assigne(1.,0.);
	    }else if(objet[jq].nature==le_dioptre_spherique){
		coco=pc_in.pt.x*pc_in.coeff-pc_in.pt.y;
		a_trinome=1+pc_in.coeff*pc_in.coeff;
		bp_trinome=-pc_in.coeff*(coco+objet[jq].center.y)-objet[jq].center.x;
		c_trinome=coco*(coco+2*objet[jq].center.y)+objet[jq].center.longueur_carre()-objet[jq].carac*objet[jq].carac;
		delta_trinome=bp_trinome*bp_trinome-a_trinome*c_trinome;
		if(delta_trinome>=0){
		    if(objet[jq].carac>0)
			pt_end.x=(-bp_trinome+Math.sqrt(delta_trinome))/a_trinome;
		    else
			pt_end.x=(-bp_trinome-Math.sqrt(delta_trinome))/a_trinome;
		    pt_end.y=pc_in.pt.y+(pt_end.x-pc_in.pt.x)*pc_in.coeff;
		    tata.assigne_soustrait(pt_end,objet[jq].center);
		    if(objet[jq].carac<0)
			tata.multiplie_cst(-1.);
		    tata.divise(tata.longueur());
		}
	    }
	    toto.assigne_soustrait(pt_end,pc_in.pt);
	    toto.divise(toto.longueur());
	    toto.rotation_x(tata,false);
	    toto.after_diffraction_rel_x(objet[jq].indice_droite/objet[jq].indice_gauche);
	    toto.rotation_x(tata,true);
	    coef_ang_fin=toto.y/toto.x;
	}else if(objet[jq].nature==la_lentille){
	    y_sur_lobjet=pc_in.pt.y+pc_in.coeff*(objet[jq].pt.x -pc_in.pt.x);
	    pt_end=new point(objet[jq].pt.x,y_sur_lobjet);
	    y_sur_le_plan_focal=objet[jq].pt.y+objet[jq].carac*pc_in.coeff;
	    coef_ang_fin=(y_sur_le_plan_focal-y_sur_lobjet)/objet[jq].carac;
	}
	return(new point_coeff(pt_end,coef_ang_fin));
    }
    point_coeff rayon_incident_coefficient_refracte_vertical(int num_obj_lens,point_coeff pc_in){
	x_sur_lobjet=pc_in.pt.x+pc_in.coeff*(objet[num_obj_lens].pt.y -pc_in.pt.y);
	pt_final.assigne(x_sur_lobjet,objet[num_obj_lens].pt.y);
	x_sur_le_plan_focal=objet[num_obj_lens].pt.x+objet[num_obj_lens].carac*pc_in.coeff;
	coef_ang_fin=(x_sur_le_plan_focal-x_sur_lobjet)/objet[num_obj_lens].carac;
	return(new point_coeff(pt_final,coef_ang_fin));
    }
    class cos_dir{
	point pt;double z;
	cos_dir(double x1,double y1,double z1){
	    pt=new point(x1,y1);z=z1;
	}
	void assigne(double x1,double y1,double z1){
	    pt.assigne(x1,y1);z=z1;
	    if(Math.abs(longueur_carre()-1.)>0.00001){
		pt.print("longueur_carre()"+longueur_carre()+" z "+(float)z+" pt ");
		objet[1000]=null;
	    }
	}
	void assigne(cos_dir c){
	    pt.x=c.pt.x;pt.y=c.pt.y;z=c.z;
	}
	boolean egal(cos_dir c){
	    return (Math.abs(pt.x-c.pt.x)<0.000001&& Math.abs(pt.y-c.pt.y)<0.000001&&Math.abs(z-c.z)<0.000001);
	}
	double xpy(){
	    return pt.x+pt.y;
	}
	double xmy(){
	    return pt.x-pt.y;
	}
	double y_sur_x(){
	    return pt.y/pt.x;
	}
	double x_sur_y(){
	    return pt.x/pt.y;
	}
	double z_sur_longueur_x_y(){
	    return z/pt.longueur();
	}	
	double longueur_carre(){
	    return pt.longueur_carre()+z*z;
	}
	public void print(String st){
	    System.out.println(st+ " x "+(float)pt.x+" y "+(float)pt.y+" z "+z+" longueur_carre() "+(float)longueur_carre());
	}
	public void apres_diffraction_rel_x(double indice){
	    //print("avant indice "+indice);
	    z/=indice;
	    pt.y/=indice;
	    pt.x=Math.sqrt(1.-pt.y*pt.y-z*z);;
	    //print("apres ");
	}
	public void apres_diffraction_rel_y(double indice){
	    //print("avant indice "+indice);
	    z/=indice;
	    pt.x/=indice;
	    pt.y=Math.sqrt(1.-pt.x*pt.x-z*z);;
	    //print("apres ");
	}
    }
    class point_y_z{
	double y=0.,z=0.;
        point_y_z(double y1,double z1){
	    y=y1;z=z1;
	}
	void assigne(double y1,double z1){
	    y=y1;z=z1;
	}
	void assigne(point_y_z pt){
	    y=pt.y;z=pt.z;
	}
	void assigne_facteur(point_y_z pt,double d){
	    y=pt.y*d;z=pt.z*d;
	}
	void additionne(point_y_z pt){
	    y+=pt.y;z+=pt.z;
	}
	public double longueur(){
	    return(Math.sqrt(y*y+z*z));
	}
	public double longueur_carre(){
	    return (y*y+z*z);
	}
	public void additionne_point_facteur(point_y_z a,double c){
	    y+=c*a.y;
	    z+=c*a.z;
	}

	public void print(String st){
	    System.out.println(st+ " y "+(float)y+" z "+(float)z);
	}
    }
    class point_coeff{
	point pt;double coeff;   
	point_coeff(point pt1,double coeff1){
	    pt=new point(pt1);
	    coeff=coeff1;
	}
	point_coeff(point_coeff pc){
	    pt=new point(pc.pt);
	    coeff=pc.coeff;
	}
	point_coeff(double x,double y,double coeff1){
	    pt=new point(x,y);
	    coeff=coeff1;
	}
	void assigne(point_coeff p){
	    pt.assigne(p.pt);
	    coeff=p.coeff;
	}
	void assigne(double x,double y,double coeff1){
	    pt.assigne(x,y);
	    coeff=coeff1;
	}
	void assigne(point pt1,double coeff1){
	    pt.assigne(pt1);
	    coeff=coeff1;
	}
	point croisement(point_coeff pcb){
	    ordonnee_origine_a=pt.y-coeff*pt.x;
	    ordonnee_origine_b=pcb.pt.y-pcb.coeff*pcb.pt.x;
	    if(coeff!=pcb.coeff)
		xiimage=(ordonnee_origine_b-ordonnee_origine_a)/(coeff-pcb.coeff);
	    else
		xiimage=30000.;
	    yiimage=coeff*xiimage+ordonnee_origine_a;
		
	    return(new point(xiimage,yiimage));
	}

	public void print(String st){
	    System.out.println(st+ " pt.x "+(float)pt.x+" pt.y "+(float)pt.y+" coeff "+coeff);
	}
    }
    void rayon_passant_par_2_points_a_travers_une_lentille(int num_obj_lens,double x1,double y1,double x2,double y2){
	double delta=(objet[num_obj_lens].pt.x-x1)*(objet[num_obj_lens].pt.x-x2+objet[num_obj_lens].carac)+(objet[num_obj_lens].pt.x-x1-objet[num_obj_lens].carac)*(objet[num_obj_lens].pt.x-x2+objet[num_obj_lens].carac);
	coef_ang_1=(x2-objet[num_obj_lens].pt.x)/delta;
	coef_ang_2=(x1-objet[num_obj_lens].pt.x)/delta;
    }

    class plaque_photographique extends Frame{
	int top=0;int left=400; int bottom = 400;int right = 870;
	Graphics grp_repr;
	point centre;couleur cocol;
	public plaque_photographique(String s,int num_plaque){
	    super(s);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			/*
			while(occupied){
			}
			*/
			if(plaque_photo!=null)
			    plaque_photo=null;
			dispose();
		    };
		});		
	    //setLocation(left,top);
	    pack();
	    setVisible(true);
	    centre=new point((right-left)/2.,(bottom-top)/2.);
	    cocol=new couleur(0,0,0);
	    setSize(right-left,bottom-top);
	    setLocation(left,top+num_plaque*350);
	    grp_repr= getGraphics(); 
	    /*
	    image_plaque=createImage(right-left,bottom-top);
	    gTampon_plaque=image_plaque.getGraphics();
	    tracker_plaque=new MediaTracker(this);
	    tracker_plaque.addImage(image_plaque,1); 
	    */
	}
    }
    void re_dessin(){
	if(diffraction||perot_lame||michelsonne){
	    du_nouveau_a_voir=true;
	    dessin();
	}
    }
    void remplis_de_couleur(objectt dioptre_sph1,objectt dioptre_sph2,int icolor,boolean non_secants){
	int ordonnee=0;
	int depart_x=(int)dioptre_sph1.pt.x,arrivee_x=1000;
	boolean signe_initial=false, signe_final=false;
	if(non_secants)
	    System.out.println(" entree remplis_de_couleur " );
	if(icolor==1)
	    gTampon_appareil.setColor(Color.green);
	else if(icolor==2)
	    gTampon_appareil.setColor(Color.yellow);
	cici=objet[1].longueur;
	if(dioptre_sph2!=null&&lentle_3==null)//il semblerait que dioptre_sph2 et lentle_3 ne fassent parfois qu'un
	    if(i_demarre!=60&&i_demarre!=61)//il semblerait que dioptre_sph2 et lentle_3 ne fassent parfois qu'un
		if(dioptre_sph2.longueur<cici)
		    cici=dioptre_sph2.longueur;
	for(int i=0;i<cici;i++){
	    ordonnee=(int)y_banc+i;
	    if(i_demarre==60){
		depart_x=(int)Math.round(objet[1].pt.x);
		toto_int=-1;
		for(int j=2;j<nb_objets;j++)
		    if(objet[j].nature==le_dioptre_plan)
			toto_int=j;
		if(toto_int==-1)
		    if(objet[1].inversion_d_indice)
			arrivee_x=0;
		    else
			arrivee_x=1000;
		else
		    arrivee_x=(int)objet[toto_int].pt.x;
	    }else{
		toto_int=1;
		if(dioptre_sph1!=null)
		    if(dioptre_sph1.carac<0)
			toto_int=-1;
		depart_x=(int)Math.round(dioptre_sph1.center.x+toto_int*Math.sqrt(dioptre_sph1.carac*dioptre_sph1.carac-i*i));
		if(i_demarre!=61){
		    toto_int=1;
		    if(dioptre_sph2.carac<0)
			toto_int=-1;
		    arrivee_x=(int)Math.round(dioptre_sph2.center.x+toto_int*Math.sqrt(dioptre_sph2.carac*dioptre_sph2.carac-i*i));
		}else
		    if(objet[1].inversion_d_indice)
			arrivee_x=0;
		    else
			arrivee_x=1000;
	    }
	    if(i==0)
		signe_initial=arrivee_x>depart_x;
	    signe_final=arrivee_x>depart_x;
	    if(signe_initial!=signe_final)
		break;
	    if(non_secants)
		System.out.println(" depart_x "+depart_x+" arrivee_x  "+arrivee_x);
	    gTampon_appareil.drawLine(depart_x,ordonnee,arrivee_x,ordonnee);
	    ordonnee=(int)Math.round(y_banc-i);
	    gTampon_appareil.drawLine(depart_x,ordonnee,arrivee_x,ordonnee);
	    /*
	    if(i_demarre==60||i_demarre==61){
		//gTampon_appareil.setColor(Color.red);
		gTampon_appareil.drawString(" carac2 "+objet[1].carac2+" carac3 "+objet[1].carac3,10,100);
		gTampon_appareil.drawString(" indice_gauche "+objet[1].indice_gauche+" indice_droite "+objet[1].indice_droite+" nb_objets "+nb_objets,10,120);
	    }
	    */
	}	    
    }
    void dessin(){
	if(du_nouveau_a_voir){
	    System.out.println("entree dans dessin i_ens "+i_ens);
	    occupied=true;
	    int x1=0,y1=0,ppv=0;
	    subject.eraserect(gTampon_appareil,0,0,800,1600,Color.white);
	    gTampon_appareil.setColor(Color.black);

	    if(interferometrie)
		interferon.initialise_dessin();
	    toto.assigne(0.,y_banc);
	    titi.assigne(800.,y_banc);
	    drawlinh(titi,toto,Color.black);
	    //if(i_demarre>=60&&i_demarre<=71||i_demarre==90)
	    if(i_demarre==9){
		remplis_de_couleur(objet[1],objet[3],2,false);
		if(nb_objets==6)
		    remplis_de_couleur(objet[4],objet[5],1,true);
	    }
	    if(i_demarre>=60&&i_demarre<=71||i_demarre==9)
		remplis_de_couleur(objet[1],objet[2],1,false);
	    for(int iq=0;iq<nb_objets;iq++){
		if(!vibration)
		    objet[iq].pt.print("$$$$$$ iq "+iq+" objet[iq].nature "+objet[iq].nature+" objet[iq].num_obj "+objet[iq].num_obj+" objet[iq].nb_pt_a_deplacer "+objet[iq].nb_pt_a_deplacer+" objet[iq].pt ");
		gTampon_appareil.setColor(Color.black);
		objet[iq].dessine();
		if(points_rouges_allumes)
		    objet[iq].met_du_rouge();
	    }
	    if(i_demarre==9){
		point top_left=new point(zer);int demi_angle=0;
		gTampon_appareil.setColor(Color.red);
		top_left.assigne(objet[3].center.x-Math.abs(objet[3].carac),objet[3].center.y-Math.abs(objet[3].carac));	  
		demi_angle=(int)(Math.asin(80/Math.abs(objet[3].carac))*180/pi+0.5);
		tata_int=-2*demi_angle;
		for(int i=0;i<=2;i++)
		gTampon_appareil.drawArc((int)(top_left.x+i+0.49),(int)(top_left.y+0.49),(int)(2*Math.abs(objet[3].carac)+0.49),(int)(2*Math.abs(objet[3].carac)+0.49),demi_angle,tata_int);    
	    }
	    if(!interferometrie){
		gTampon_appareil.drawLine(0,(int)y_banc,1000,(int)y_banc);
		optic_geo.trouve_et_dessine_image();
	    }else{
		interferon.dessine_interferometre();
		if(!(diffraction||perot_lame&&interferon!=lame_par_reflex))
		    gTampon_appareil.drawImage(image_intensites,(int)photom.pt.x-10,0,subject.ensemble[i_ens]);
		else 
		    gTampon_appareil.drawImage(image_intensites,(int)photom.pt.x-100,0,subject.ensemble[i_ens]);
	    }	    
	    dessine_l_image_de_l_appareil();
	    du_nouveau_a_voir=false;
	    occupied=false;
	}
	if(ajout_lentille_oeil)
	    ajout_lentille_oeil=false;
    }
    void dessine_l_image_de_l_appareil(){
	try {
	    tracker_appareil.waitForAll(); 
	}
	catch (InterruptedException e){
	    System.out.println(" image pas arrivee?");
	}
	if(grande_taille)
	    grp_c.drawImage(image_appareil,0,0,2*(right_ens_cyl-left_ens_cyl),2*(bottom_ens_cyl-top_ens_cyl),subject.ensemble[i_ens]);
	else
	    grp_c.drawImage(image_appareil,0,0,right_ens_cyl-left_ens_cyl,bottom_ens_cyl-top_ens_cyl,subject.ensemble[i_ens]);
	if(grande_taille)
	    grp_c.drawImage(image_appareil,0,0,2*(right_ens_cyl-left_ens_cyl),2*(bottom_ens_cyl-top_ens_cyl),subject.ensemble[i_ens]);
	//if(grande_taille)
	//  objet[1001]=null;
    }
}
class triple_double{
    double r,v,b;
    public triple_double(double r1,double v1,double b1){
	r=r1;v=v1;b=b1;
    }
    public void assigne(triple_double c){
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne(double r1,double v1,double b1){
	r=r1;v=v1;b=b1;
    }
    public void assigne(couleur c){
	r=c.r;v=c.v;b=c.b;
    } 
    public void assigne_facteur(triple_double c,double d){
	r=(double)(c.r*d);v=(double)(c.v*d);b=(double)(c.b*d);
    }
    public void remise_a_zero(){
	r=0;v=0;b=0;
    }
    public void multiplie(double f){
	r=r*f;v=v*f;b=b*f;
    }
    public void multiplie(triple_double f){
	r*=f.r;v*=f.v;b*=f.b;
    }
    public void divise(double f){
	r=r/f;v=v/f;b=b/f;
    }
    public void print(String st){
	System.out.println(st+ " r "+(float)r+" v "+(float)v+" b "+(float)b);
    }
}
class point{
    double x,y;    
    static final double pi=3.141592652;
    public point(double xi,double yi){
	x=xi;y=yi;
    }
    public point(int xi,int yi){
	x=(double)xi;y=(double)yi;
    }
    public point(point a){
	x=a.x;y=a.y;
    }
    public void assigne(double xi,double yi){
	x=xi;y=yi;
    }
    public void assigne(point a){
	x=a.x;y=a.y;
    }
    public void assigne_facteur(point a,double f){
	x=a.x*f;y=a.y*f;
    }
    public void assigne_diviseur(point a,double f){
	x=a.x/f;y=a.y/f;
    }
    public void soustrait(point a){
	x-=a.x;y-=a.y;
    }
    public void assigne_additionne(point a,point b){
	x=a.x+b.x;y=a.y+b.y;
    }
    public void assigne_soustrait(point a,point b){
	x=a.x-b.x;y=a.y-b.y;
    }
    public double distance_carre(point pt){
	return (x-pt.x)*(x-pt.x)+(y-pt.y)*(y-pt.y);
    }
    public double carre(){
	return(x*x+y*y);
    }
    public double distance(point pt){
	return  Math.sqrt((x-pt.x)*(x-pt.x)+(y-pt.y)*(y-pt.y));
    }
    public void additionne_point_facteur(point a,double c){
	x+=c*a.x;
	y+=c*a.y;
    }
    public double scalaire(point c){
	return(x*c.x+y*c.y);
    }
    public double vectoriel(point a){
	return (x*a.y-y*a.x);
    }
    public double longueur(){
	return(Math.sqrt(x*x+y*y));
    }
    public double longueur_carre(){
	return (x*x+y*y);
    }
    public double direction(){
	double direction=0.;
	if(Math.abs(x)>Math.abs(y)){
	    direction=Math.asin(y/longueur());
	    if(x<0.)
		if(y>0.)
		    direction=pi-direction;
		else
		    direction=-pi-direction;
	}else{
	    direction=Math.acos(x/longueur());
	    if(y<0.)direction=-direction;
	}
	return direction;
    }
    public void additionne(point a){
	x+=a.x;y+=a.y;
    }
    public void additionne(double xi,double yi){
	x+=xi;y+=yi;
    }
    public void additionne_facteur_print(point a,double c){
	a.print(" x "+x+" y "+y+"  a ");
	x+=c*a.x;
	y+=c*a.y;
	a.print(" c "+c+" x "+x+" y "+y+"  a ");
    }
    public void additionne_facteur(point a,double c){
	x+=c*a.x;
	y+=c*a.y;
    }
    public void multiplie_cst(double a){
	x*=a;
	y*=a;
    }
    public double somme_composantes(){
	return (x+y);
    }
    public double y_sur_x(){
	    return y/x;
	}

    public void divise(double a){
	x/=a;
	y/=a;
    }
    
    public void projections(double cosinus,double sinus){
	double x_p=x;double y_p=y;
	x=-sinus*x_p+cosinus*y_p;
	y=cosinus*x_p+sinus*y_p;
    }
    public void zero(){
	x=0.;y=0.;
    }
    public void symetrique(point d){
	double coco=scalaire(d);
	double cucu=vectoriel(d);
	x=d.x*coco-d.y*cucu;
	y=d.x*cucu+d.y*coco;
    }
    public void symetrique_retour(point d){
	double coco=scalaire(d);
	double cucu=vectoriel(d);
	x=-d.x*coco+d.y*cucu;
	y=-d.x*cucu-d.y*coco;
    }
    public void rotation(double angle){
	double cos=(double)Math.cos(angle);double sin=(double)Math.sin(angle);
	double x_p=x;double y_p=y;
	x=cos*x_p-sin*y_p;
	y=sin*x_p+cos*y_p;
    }
    public void rotation_x(point pt_ang_x, boolean directe){
	double x_p=x;double y_p=y;
	if(directe){
	    x=pt_ang_x.x*x_p-pt_ang_x.y*y_p;
	    y=pt_ang_x.y*x_p+pt_ang_x.x*y_p;
	}else{
	    x=pt_ang_x.x*x_p+pt_ang_x.y*y_p;
	    y=-pt_ang_x.y*x_p+pt_ang_x.x*y_p;
	}
    }
    public void rotation_y(point pt_ang_y, boolean directe){
	double x_p=x;double y_p=y;
	if(directe){
	    x=pt_ang_y.y*x_p-pt_ang_y.x*y_p;
	    y=pt_ang_y.x*x_p+pt_ang_y.y*y_p;
	}else{
	    x=pt_ang_y.y*x_p+pt_ang_y.x*y_p;
	    y=-pt_ang_y.x*x_p+pt_ang_y.y*y_p;
	}
    }
	public void after_diffraction_rel_x(double indice){
	    //print("avant indice "+indice);
	    y/=indice;
	    x=Math.sqrt(1.-y*y);;
	    //print("apres ");
	}

    public void print(String st){
	System.out.println(st+ " x "+x+" y "+y);
    }
    public void print_float(String st){
	System.out.println(st+ " x "+(float)x+" y "+(float)y);
    }
}
abstract class triple_entier{
    int r,v,b;
    public triple_entier(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
    }
    public triple_entier(couleur c1){
	r=c1.r;v=c1.v;b=c1.b;
    }
    public void assigne(couleur c){
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne_facteur(triple_entier c,double d){
	r=(int)(c.r*d);v=(int)(c.v*d);b=(int)(c.b*d);
    }
    public void remise_a_zero(){
	r=0;v=0;b=0;
    }
    public void multiplie(double f){
	r=(int)(r*f);v=(int)(v*f);b=(int)(b*f);
    }
    public void divise(double f){
	r=(int)(r/f);v=(int)(v/f);b=(int)(b/f);
    }
    public boolean egale(couleur a){
	return ((r==a.r)&&(v==a.v)&&(b==a.b));
    }
    abstract public void assigne(int r1,int v1,int b1);
    public void print(String st){
	System.out.println(st+ " r "+r+" v "+v+" b "+b);
    }
}
class triple_int extends triple_entier{
    public triple_int(int r1,int v1,int b1){
	super(r1,v1,b1);
    }
    public triple_int(couleur c1){
	super(c1);
    }
    public void assigne(couleur c){
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
    }
    public void assigne_facteur(triple_int c,double d){
	r=(int)(c.r*d);v=(int)(c.v*d);b=(int)(c.b*d);
    }
}
class couleur extends triple_entier{
    Color col;
    public couleur(int r1,int v1,int b1){
	super(r1,v1,b1);
	col=new Color(r,v,b);
	//	    if(col==rouge) marche pas!!!!!
    }
    public couleur(couleur c1){
	super(c1);
	col=new Color(c1.r,c1.v,c1.b);
    }
    public void assigne(couleur c){
	col=c.col;
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne(triple_double c){
	r=(int)c.r;v=(int)c.v;b=(int)c.b;
	col=new Color(r,v,b);

    }
    public void multiplie(double f){
	r=(int)(r*f);v=(int)(v*f);b=(int)(b*f);
    }
    public void assigne(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
	col=new Color(r,v,b);
    }
}



