package pt.uc.dei.cms.incerto.interfaces.gui;

import java.util.logging.LoggingMXBean;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import org.semanticweb.owl.profiles.AxiomNotAllowed;

import pt.uc.dei.cms.incerto.exceptions.MarkovLogicEngineException;
import pt.uc.dei.cms.incerto.exceptions.OntologyProcessorException;
import pt.uc.dei.cms.incerto.firstorderlogic.model.Formula;
import pt.uc.dei.cms.incerto.model.Evidence;
import pt.uc.dei.cms.incerto.model.MLN;
import pt.uc.dei.cms.incerto.model.Query;
import pt.uc.dei.cms.incerto.model.ReasoningResults;
import pt.uc.dei.cms.incerto.onto.parserOWLAPI;
import pt.uc.dei.cms.incerto.utils.LoggerUtils;
import pt.uc.dei.cms.incerto.utils.settings.IncertoSettings;

/**
 * Graphical User Interface (GUI)<br>
 * Main Window
 * @author Pedro Oliveira
 *
 */
public class GUIMain extends javax.swing.JFrame {

	private static final long serialVersionUID = 8268251975002579721L;
	

    public GUIMain() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAxioms = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableIndividuals = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableWeightLearning = new javax.swing.JTable();
        jButtonStartWeightLearning = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableInference = new javax.swing.JTable();
        jButtonStartInference = new javax.swing.JButton();
        jButtonLoadOntology = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        
        
        fileChooser = new JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Axioms"));

        jTableAxioms.setModel(new AxiomsTableModel());
        jScrollPane1.setViewportView(jTableAxioms);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Individuals"));

        jTableIndividuals.setModel(new AssertionsTableModel());
        jScrollPane2.setViewportView(jTableIndividuals);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Weight Learning"));

        jTableWeightLearning.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Engine", "Alchemy"},
                {"Algorithm", "Default"}
            },
            new String [] {
                "Property", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableWeightLearning);

        jButtonStartWeightLearning.setText("Start Weight Learning");
        jButtonStartWeightLearning.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonStartWeightLearningMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonStartWeightLearning, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonStartWeightLearning))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Inference"));

        jTableInference.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Engine", "Alchemy"},
                {"Algorithm", "-ms"},
                {"Query", null}
            },
            new String [] {
                "Property", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTableInference);

        jButtonStartInference.setText("Start Inference");
        jButtonStartInference.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonStartInferenceMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonStartInference, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonStartInference))
        );

        jButtonLoadOntology.setText("Load Ontology");
        jButtonLoadOntology.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jButtonLoadOntologyMouseReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 102, 255));
        jLabel1.setText("http://code.google.com/p/incerto");

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("<html><body><b>Incerto</b><p>A Probabilistic Reasoner<p>for the Semantic Web<p> based on Markov Logic</body></html>");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jButtonLoadOntology, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jButtonLoadOntology)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        pack();
        //fillAxiomsTable(null);
    }

    private void jButtonLoadOntologyMouseReleased(java.awt.event.MouseEvent evt) {
    	int res = fileChooser.showOpenDialog(GUIMain.this);
    	
    	if(res == JFileChooser.APPROVE_OPTION)
    	{
    		String filename = fileChooser.getSelectedFile().getAbsolutePath();
    		try {
				currentMLN = new parserOWLAPI().onto2MLN(filename);
				fillAxiomsTable(currentMLN);
				fillAssertionsTable(currentMLN);
			} catch (OntologyProcessorException e) {
				LoggerUtils.addError("Error on loading ontology "+filename, e);
				JOptionPane.showMessageDialog(GUIMain.this, "Error on loading ontology "+filename,"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
    		
    	}
        
    }

    private void jButtonStartWeightLearningMouseReleased(java.awt.event.MouseEvent evt) {
        if(currentMLN!=null)
        {
        	MLN mln = (MLN)currentMLN.clone();
        	mln = updateMLN(mln);
        	try {
				mln = IncertoSettings.getInstance().ML_ENGINE.weightlearning(mln, new Evidence(mln.getEvidences()));
				currentMLN = updateIndividuals(mln, currentMLN);
				fillAxiomsTable(currentMLN);
				fillAssertionsTable(currentMLN);
        	} catch (MarkovLogicEngineException e) {
				LoggerUtils.addError("Error on weight learning", e);
				JOptionPane.showMessageDialog(GUIMain.this, "Error on weight learning","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
        	JOptionPane.showMessageDialog(GUIMain.this, "Weight learning done!","Information",JOptionPane.INFORMATION_MESSAGE);
        }
        else
        	JOptionPane.showMessageDialog(GUIMain.this, "Please load a valid ontology","Information",JOptionPane.INFORMATION_MESSAGE);
    }

    private void jButtonStartInferenceMouseReleased(java.awt.event.MouseEvent evt) {
    	if(currentMLN!=null)
        {
    		MLN mln = (MLN)currentMLN.clone();
        	mln = updateMLN(mln);
    		Object query = jTableInference.getValueAt(2, 1);
    		if(query==null)
    		{
    			JOptionPane.showMessageDialog(GUIMain.this, "Invalida query "+query,"Error",JOptionPane.ERROR_MESSAGE);
				return;
    		}
    		
    		Query q = Query.parseQuery((String)query, true);
    		try {
				ReasoningResults res = IncertoSettings.getInstance().ML_ENGINE.inference(mln, new Evidence(mln.getEvidences()), q);
		        new GUIInferenceResults(res).setVisible(true);
    		} catch (MarkovLogicEngineException e) {
				LoggerUtils.addError("Error on inference", e);
				JOptionPane.showMessageDialog(GUIMain.this, "Error on inference","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
        }
        else
        	JOptionPane.showMessageDialog(GUIMain.this, "Please load a valid ontology","Information",JOptionPane.INFORMATION_MESSAGE);
    
    }

    
    private void fillAxiomsTable(MLN mln)
    {
    	AxiomsTableModel model = new AxiomsTableModel();   	
    	for(Formula f: mln.getFormulas())
    		model.addElement(f, true);
    	jTableAxioms.setModel(model);
    }
    
    private void fillAssertionsTable(MLN mln)
    {
    	AssertionsTableModel model = new AssertionsTableModel();   	
    	for(Formula f: mln.getEvidences())
    		model.addElement(f, true);
    	jTableIndividuals.setModel(model);
    }
    
    public MLN updateMLN(MLN mln)
    {
    	mln.clearEvidences();
    	mln.clearFormulas();
   	
    	AxiomsTableModel model = (AxiomsTableModel)jTableAxioms.getModel();  	
    	for(InternalAxiom axiom: model.getElements())
    	{
    		if(axiom.use)
    			mln.addFormula(axiom.f);
    	}
    	
    	AssertionsTableModel model2 = (AssertionsTableModel)jTableIndividuals.getModel();  	
    	for(InternalAxiom axiom: model2.getElements())
    	{
    		if(axiom.use)
    			mln.addEvidence(axiom.f);
    	}
    	return mln;
    }
    
    public MLN updateIndividuals(MLN one, MLN two)
    {
    	one.clearEvidences();
    	for(Formula f: two.getEvidences())
    		one.addEvidence(f);
    	return one;
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButtonLoadOntology;
    private javax.swing.JButton jButtonStartInference;
    private javax.swing.JButton jButtonStartWeightLearning;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableAxioms;
    private javax.swing.JTable jTableIndividuals;
    private javax.swing.JTable jTableInference;
    private javax.swing.JTable jTableWeightLearning;
    // End of variables declaration
    
    private javax.swing.JFileChooser fileChooser;
    
    private MLN currentMLN;

}