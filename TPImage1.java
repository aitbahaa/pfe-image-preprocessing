import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;         
import javax.swing.filechooser.FileFilter; 
import javax.swing.BorderFactory;
import javax.swing.JRadioButton;
import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.File;

public class TPImage extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JLabel labelselec = new JLabel("Choisissez vos images :");
	private JLabel labelChoix = new JLabel("Choisissez votre traitement:");
	private JLabel labelTraitement = new JLabel ("vos Images :");
	private JLabel labelWidth = new JLabel ("Width :");
	private JLabel labelHeight = new JLabel ("Height :");
		/* COMBOBOX */
	private JComboBox traitement = new JComboBox();
	private JButton btn = new JButton("Selectionner");
	private JButton btnOK = new JButton("OK");
	private JButton btnANL = new JButton("Annuler");
		/*JTextField*/
	private JTextField Width = new JTextField();
	private JTextField Height = new JTextField();
		/*FILECHOOSER*/
	private JFileChooser choix = new JFileChooser();
		/*RadioButton*/
	JRadioButton RGris = new JRadioButton("Traitement en Gris");
	JRadioButton RColor = new JRadioButton("Traitement en Couleur");
	ButtonGroup R = new ButtonGroup();
	private File[] fls = null;

	private JPanel p0 = new JPanel(new FlowLayout());
	private JPanel p1 = new JPanel(new FlowLayout());
	private JPanel p2 = new JPanel(new FlowLayout());
	private JPanel p3 = new JPanel(new FlowLayout());
	private GridLayout G=new GridLayout(4,1);
	
	TPImage()
	{
		traitement.addItem("Binarise");
		traitement.addItem("recadrage Dynamique");
		traitement.addItem("filtrage Moyenneur");
		traitement.addItem("equalization");
		traitement.addItem("filtrage Gaussien");
		traitement.addItem("filtrage GaussienDe15");
		traitement.addItem("filtrage Gradient");
		traitement.addItem("Lissage Minmax");
		traitement.addItem("filtrage Adaptatif");
		traitement.addItem("filtrage Nagao");
		/* LABEL */
		setPreferredSize(new Dimension(450,200));
		btnOK.setPreferredSize(new Dimension(120,30));
		btnANL.setPreferredSize(new Dimension(120,30));
		choix.setMultiSelectionEnabled(true);
		choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
		RGris.setSelected(true);
		RGris.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				traitement.removeAllItems();
				traitement.addItem("Binarise");
				traitement.addItem("recadrage Dynamique");
				traitement.addItem("filtrage Moyenneur");
				traitement.addItem("equalization");
				traitement.addItem("filtrage Gaussien");
				traitement.addItem("filtrage GaussienDe15");
				traitement.addItem("filtrage Gradient");
				traitement.addItem("Lissage Minmax");
				traitement.addItem("filtrage Adaptatif");
				traitement.addItem("filtrage Nagao");
			}
		});
		RColor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				traitement.removeAllItems();
				traitement.addItem("Binarise");
				traitement.addItem("Negation");
				traitement.addItem("Flip");
				traitement.addItem("Filtrage Moyenneur");
				traitement.addItem("Contrast Correction");
			}
		});
		setLayout(G);
		R.add(RGris);R.add(RColor);
		p0.add(RGris);p0.add(RColor);
		p1.add(labelselec);p1.add(btn);	
		p2.add(labelChoix);p2.add(traitement);
		p3.add(btnOK);p3.add(btnANL);
		p1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		p2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		p3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		add(p0);add(p1);add(p2);add(p3);
		 
		
	
		choix.setAcceptAllFileFilterUsed(false);
		String exetension[]={"raw"};
		FileFilter imagesFilter = new FileNameExtensionFilter("raw",exetension);
		choix.addChoosableFileFilter(imagesFilter);
		
		
		btn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int retour = choix.showOpenDialog(null);
				if(retour == JFileChooser.APPROVE_OPTION)
				{
					fls=choix.getSelectedFiles(); 
				}
			}
		});
		
	
		btnOK.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
			{
				if(fls.length>0)
				{
					
					String pathnewdir=fls[0].getAbsolutePath();
					pathnewdir= pathnewdir.substring(0, pathnewdir.lastIndexOf(File.separator)+1);
					pathnewdir+="Images_traitees";
					File dir=new File(pathnewdir);
					dir.mkdirs();
					if(RGris.isSelected())
					{
						if (traitement.getSelectedItem().toString().equals("Binarise"))
						{
							for( int i = 0; i<fls.length; i++)
							{ 
								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									
									if((!(Width.getText().length()==0) && !(Height.getText().length()==0)) )	
									{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.binarise(127);
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
									}else
									{
										JOptionPane.showMessageDialog(rootPane, 
										"veuillez remplir les champs vides!", "Message d'erreur",
										JOptionPane.INFORMATION_MESSAGE);
										setVisible(true);
									}
								}
							
							}
						}
						else if(traitement.getSelectedItem().toString().equals("recadrage Dynamique"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0)  
											&& !(Height.getText().length()==0)) )
											
										{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.recadrage_Dynamique();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
										}else{
											JOptionPane.showMessageDialog(rootPane, 
											"veuillez remplir les champs vides!", "Message d'erreur",
											JOptionPane.INFORMATION_MESSAGE);
											setVisible(true);
											}
													
								}
							}
							
						}
						else if(traitement.getSelectedItem().toString().equals("filtrage Moyenneur"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0)  
											&& !(Height.getText().length()==0)) )
											
										{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.filtrageMoyenneur();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
										}else{
											JOptionPane.showMessageDialog(rootPane, 
											"veuillez remplir les champs vides!", "Message d'erreur",
											JOptionPane.INFORMATION_MESSAGE);
											setVisible(true);
											}
									
													
								}
							}
							
						}
						else if(traitement.getSelectedItem().toString().equals("equalization"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0)  
											&& !(Height.getText().length()==0)) )
											
										{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.equalization();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
										}else{
											JOptionPane.showMessageDialog(rootPane, 
											"veuillez remplir les champs vides!", "Message d'erreur",
											JOptionPane.INFORMATION_MESSAGE);
											setVisible(true);
											}
													
								}
							}
							
						}
						else if(traitement.getSelectedItem().toString().equals("filtrage Gaussien"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0)  
											&& !(Height.getText().length()==0)) )
											
										{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.filtrageGaussien();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
										}else{
											JOptionPane.showMessageDialog(rootPane, 
											"veuillez remplir les champs vides!", "Message d'erreur",
											JOptionPane.INFORMATION_MESSAGE);
											setVisible(true);
											}
									
													
								}
							}
							
						}
						else if(traitement.getSelectedItem().toString().equals("filtrage GaussienDe15"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0)  
											&& !(Height.getText().length()==0)) )
											
										{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.filtrageGaussienDe15();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
										}else{
											JOptionPane.showMessageDialog(rootPane, 
											"veuillez remplir les champs vides!", "Message d'erreur",
											JOptionPane.INFORMATION_MESSAGE);
											setVisible(true);
											}
									
													
								}
							}
							
						}
						else if(traitement.getSelectedItem().toString().equals("filtrage Gradient"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0)  
											&& !(Height.getText().length()==0)) )
											
										{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.filtrageGradient();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
										}else
										{
											JOptionPane.showMessageDialog(rootPane, 
											"veuillez remplir les champs vides!", "Message d'erreur",
											JOptionPane.INFORMATION_MESSAGE);
											setVisible(true);
										}			
								}
							}
							
						}
						else if(traitement.getSelectedItem().toString().equals("Lissage Minmax"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0)  
											&& !(Height.getText().length()==0)) )
											
										{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.filtreLissageMinmax();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
										}else{
											JOptionPane.showMessageDialog(rootPane, 
											"veuillez remplir les champs vides!", "Message d'erreur",
											JOptionPane.INFORMATION_MESSAGE);
											setVisible(true);
											}
									
													
								}
							}
						
						}
						else if(traitement.getSelectedItem().toString().equals("filtrage Adaptatif"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0)  
											&& !(Height.getText().length()==0)) )
											
										{
										PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.filtrageAdaptatif();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
										}else{
											JOptionPane.showMessageDialog(rootPane, 
											"veuillez remplir les champs vides!", "Message d'erreur",
											JOptionPane.INFORMATION_MESSAGE);
											setVisible(true);
											}
									
													
								}
							}
						
						}
						else if(traitement.getSelectedItem().toString().equals("filtrage Nagao"))
						{
						
						for( int i = 0; i<fls.length; i++)
						{ 

							Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
							int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
										JOptionPane.OK_CANCEL_OPTION ,	
										JOptionPane.INFORMATION_MESSAGE,	
										NColor.icn ,	null ,	null); 
							if (rep == JOptionPane.OK_OPTION)
							{
								if((!(Width.getText().length()==0) && !(Height.getText().length()==0)) )
								{
									PImage img = new GImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
									String chemin =  fls[i].getAbsolutePath();
									img.readImage(chemin,0);
									PImage img1 = img.filtrageNagao();
									img1.saveImage(pathnewdir+File.separator+fls[i].getName());
									Width.setText("");Height.setText("");
									setVisible(false);
								}
								else
								{
									JOptionPane.showMessageDialog(rootPane,
									"veuillez remplir les champs vides!", "Message d'erreur",
									JOptionPane.INFORMATION_MESSAGE);
									setVisible(true);
								}				
							}
						}
					}
					}
					else
					{
						if(traitement.getSelectedItem().toString().equals("Binarise"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0) && !(Height.getText().length()==0)) )
									{
										PImage img = new CImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.binarise(127);
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
									}
									else
									{
										JOptionPane.showMessageDialog(rootPane,
										"veuillez remplir les champs vides!", "Message d'erreur",
										JOptionPane.INFORMATION_MESSAGE);
										setVisible(true);
									}				
								}
							}
						}
						else if(traitement.getSelectedItem().toString().equals("Negation"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0) && !(Height.getText().length()==0)) )
									{
										PImage img = new CImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.opNeg();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
									}
									else
									{
										JOptionPane.showMessageDialog(rootPane,
										"veuillez remplir les champs vides!", "Message d'erreur",
										JOptionPane.INFORMATION_MESSAGE);
										setVisible(true);
									}				
								}
							}
						}
						else if(traitement.getSelectedItem().toString().equals("Flip"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0) && !(Height.getText().length()==0)) )
									{
										PImage img = new CImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.flip();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
									}
									else
									{
										JOptionPane.showMessageDialog(rootPane,
										"veuillez remplir les champs vides!", "Message d'erreur",
										JOptionPane.INFORMATION_MESSAGE);
										setVisible(true);
									}				
								}
							}
						}
						else if(traitement.getSelectedItem().toString().equals("Filtrage Moyenneur"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0) && !(Height.getText().length()==0)) )
									{
										PImage img = new CImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.filtrageMoyenneur();
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
									}
									else
									{
										JOptionPane.showMessageDialog(rootPane,
										"veuillez remplir les champs vides!", "Message d'erreur",
										JOptionPane.INFORMATION_MESSAGE);
										setVisible(true);
									}				
								}
							}
						}
						else if(traitement.getSelectedItem().toString().equals("Contrast Correction"))
						{
							
							for( int i = 0; i<fls.length; i++)
							{ 

								Object [] tab_obj_afficher2 = new Object []{ labelWidth , Width , labelHeight , Height };
								int rep = JOptionPane.showOptionDialog(	null ,tab_obj_afficher2, "Dimensions de : " + fls[i].getName(),
											JOptionPane.OK_CANCEL_OPTION ,	
											JOptionPane.INFORMATION_MESSAGE,	
											NColor.icn ,	null ,	null); 
								if (rep == JOptionPane.OK_OPTION)
								{
									if((!(Width.getText().length()==0) && !(Height.getText().length()==0)) )
									{
										PImage img = new CImage("", Integer.valueOf(Width.getText()),Integer.valueOf(Height.getText()));
										String chemin =  fls[i].getAbsolutePath();
										img.readImage(chemin,0);
										PImage img1 = img.contrastCorrection(3);
										img1.saveImage(pathnewdir+File.separator+fls[i].getName());
										Width.setText("");Height.setText("");
										setVisible(false);
									}
									else
									{
										JOptionPane.showMessageDialog(rootPane,
										"veuillez remplir les champs vides!", "Message d'erreur",
										JOptionPane.INFORMATION_MESSAGE);
										setVisible(true);
									}				
								}
							}
						}
					}

				}
				else
				{
					JOptionPane.showMessageDialog(rootPane, 
					"veuillez choisir vos images et votre traitement !", "Message d'erreur",
					JOptionPane.INFORMATION_MESSAGE);	
				}
			}
		});
	
		
		btnANL.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false); 
			}
			
		});
	
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	
	}
}

