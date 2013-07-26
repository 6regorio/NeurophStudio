package org.neuroph.netbeans.main.easyneurons.samples.mlperceptron;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.DataSet;
import org.neuroph.core.learning.DataSetRow;
import org.neuroph.netbeans.main.TrainingController;
import org.neuroph.netbeans.main.ViewManager;
import org.neuroph.netbeans.visual.NeuralNetAndDataSet;
import org.neuroph.netbeans.main.easyneurons.errorgraph.GraphFrameTopComponent;
import org.neuroph.netbeans.main.easyneurons.samples.perceptron.PerceptronSampleTrainingSet;
import org.neuroph.netbeans.project.NeurophProjectFilesFactory;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.neuroph.netbeans.main.easyneurons.samples.mlperceptron//MultiLayerPerceptronSample//EN",
autostore = false)
public final class MultiLayerPerceptronSampleTopComponent extends TopComponent implements LearningEventListener {

    private static MultiLayerPerceptronSampleTopComponent instance;
    /** path to the icon used by the component and its open action */
//    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";
    private static final String PREFERRED_ID = "MultiLayerPerceptronSampleTopComponent";

    public MultiLayerPerceptronSampleTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(MultiLayerPerceptronSampleTopComponent.class, "CTL_MultiLayerPerceptronSampleTopComponent"));
        setToolTipText(NbBundle.getMessage(MultiLayerPerceptronSampleTopComponent.class, "HINT_MultiLayerPerceptronSampleTopComponent"));
//        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        putClientProperty(TopComponent.PROP_UNDOCKING_DISABLED, Boolean.TRUE);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(770, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link #findInstance}.
     */
    public static synchronized MultiLayerPerceptronSampleTopComponent getDefault() {
        if (instance == null) {
            instance = new MultiLayerPerceptronSampleTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the MultiLayerPerceptronSampleTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized MultiLayerPerceptronSampleTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            Logger.getLogger(MultiLayerPerceptronSampleTopComponent.class.getName()).warning(
                    "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof MultiLayerPerceptronSampleTopComponent) {
            return (MultiLayerPerceptronSampleTopComponent) win;
        }
        Logger.getLogger(MultiLayerPerceptronSampleTopComponent.class.getName()).warning(
                "There seem to be multiple components with the '" + PREFERRED_ID
                + "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    Object readProperties(java.util.Properties p) {
        if (instance == null) {
            instance = this;
        }
        instance.readPropertiesImpl(p);
        return instance;
    }

    private void readPropertiesImpl(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    ViewManager viewManager;

    InputSpacePanel inputSpacePanel;
    InformationPanel informationPanel;
    ControllsPanel sourcePanel;

    PerceptronSampleTrainingSet pst;
    DataSet trainingSet;
    int tsCount = 0;
    NeuralNetwork neuralNetwork;
    NeuralNetAndDataSet neuralNetAndDataSet;
    TrainingController trainingController;

    Thread firstCalculation = null;
    int iterationCounter = 0;


    /** Creates new form BackpropagationSample */
    public void setTrainingSetForMultiLayerPerceptronSample(PerceptronSampleTrainingSet ps) {
        setSize(770, 600);

        trainingSet = new DataSet(2, 1); // why do we need this when we have ps param?
        this.pst = ps;


         this.viewManager = ViewManager.getInstance();
       // this.displayDataBuffer = new ConcurrentLinkedQueue<NeuralNetwork>();   // red koji se ne koristi
       // link();    // povezivanje panela sa frameom

        inputSpacePanel = new InputSpacePanel();
        informationPanel = new InformationPanel();
        sourcePanel = new ControllsPanel(this);


        inputSpacePanel.setSize(570, 570);
        informationPanel.setSize(200, 50);
        sourcePanel.setSize(182, 520);

        add(inputSpacePanel);
        add(informationPanel);
        add(sourcePanel);

        inputSpacePanel.setLocation(0, 0);
        informationPanel.setLocation(570, 0);
        sourcePanel.setLocation(570, 50);

// DragNDrop - start
        this.dtListener = new DTListener();

        this.dropTarget = new DropTarget(
                  this,
                  this.acceptableActions,
                  this.dtListener,
                  true);

        this.dropTarget2 = new DropTarget(
                  inputSpacePanel,
                  this.acceptableActions,
                  this.dtListener,
                  true);

        this.dropTarget3 = new DropTarget(
                  sourcePanel,
                  this.acceptableActions,
                  this.dtListener,
                  true);
// DragNDrop - end
    }


    boolean f = false;


//    @Override
//    public void update(Observable o, Object arg) {           // pri promeni neuronske mreze, pokrece se update - observer patern
//
//        iterationCounter++;
//        if (iterationCounter % 10 == 0) {
//            NeuralNetwork nnet = neuralNetAndDataSet.getNetwork();
//
//            nnet.pauseLearning();                             // pauza
//            imagePlaying(nnet);                                  //  racunanje odgovora mreze i pozivanje crtanja
//            nnet.resumeLearning();                            //  nastavak ucenja
//        }
//
//     }

    public void train(NeuralNetwork nn)               // kada se pritisne train dugme na source panelu, logika dolazi do ovde
    {
        //if (!trainingSet.isEmpty()) trainingSet.clear();
        trainingSet = inputSpacePanel.getTrain();            //  izvlaci se training set sa tackama ulaza i izlaza iz inputSpacePanela, nije moglo drugacije
        neuralNetwork = nn;  //sp.getNet();                  // mreza je dosla iz sourcePanela koji i poziva ovu metodu

        tsCount++;
        trainingSet.setLabel("MlpSampleTrainingSet" + tsCount);


        neuralNetAndDataSet = new NeuralNetAndDataSet(neuralNetwork, trainingSet);
        trainingController = new TrainingController(neuralNetAndDataSet);

        pst.setTrainingSet(trainingSet,neuralNetwork,neuralNetAndDataSet);

        neuralNetwork.getLearningRule().addListener(this);              // dodaje se observer na mrezu i pri njenoj promeni poziva se metoda update u ovoj klasi
//        neuralNetwork.addObserver(this);

        trainingController.setLmsParams(sourcePanel.getLearningRate(),sourcePanel.getMaxError(), sourcePanel.getMaxIteration());

        LMS learningRule = (LMS) this.neuralNetAndDataSet.getNetwork().getLearningRule();

		if (learningRule instanceof MomentumBackpropagation) {
			((MomentumBackpropagation)learningRule).setMomentum(sourcePanel.getMomentum());
		}

            viewManager.openTrainingMonitorWindow(this.neuralNetAndDataSet);

            GraphFrameTopComponent graphFrame = viewManager.openErrorGraphFrame();
            graphFrame.observe(learningRule);

            NeurophProjectFilesFactory.getDefault().createNeuralNetworkFile(neuralNetwork);
            NeurophProjectFilesFactory.getDefault().createTrainingSetFile(trainingSet);
            
            trainingController.train();                // pocetak treniranja mreze
    }

    public void stop()             // zaustavljanje treniranja pozvano iz source panela
    {
        neuralNetAndDataSet.stopTraining();
    }

    public void clear()            //  restartovanje inputSpacePanela pozvano iz source panela
    {
        inputSpacePanel.clearPoints();
        pst.setTrainingSet(trainingSet,null,null);
    }

    // poziva se iz update metode za ulaze od (0,0) pa sve do (1,1) povecavajuci se za 0.02 racuna se izlaz mreze, ovo je vid testiranja na uvek istim podacima
    public void imagePlaying(NeuralNetwork nn) {

        if (nn!=null){
        for(int i=0; i<50;i++)                  // racunanje izlaza mreze za 2500 ulaza
            for (int j=0;j<50; j++)
            {
                double x = 0.0 + i*0.02;        // vrednosti ulaza x1 i x2
                double y = 1.0 - j*0.02;
                //neuralNetwork
                nn.setInput(new double[]{x, y});
                nn.calculate();
                int lastLayerIdx = nn.getLayersCount()-1;
                double v = nn.getLayerAt(lastLayerIdx).getNeuronAt(0).getOutput();    // izlaz iz mreze za ulaze x1 i x2
           //     System.out.println("ULAZ ["+i+"]["+j+"] JE:"+"["+x+"]"+"["+y+"] A IZLAZ JE "+v);
                inputSpacePanel.setGridPoints(i, j, v); // pozivanje metode koja treba da pripremi crtanje u inputSpacePanelu

            }
        }
      }

    @Override
    public void handleLearningEvent(LearningEvent le) {
        iterationCounter++;
        if (iterationCounter % 10 == 0) {
            NeuralNetwork nnet = neuralNetAndDataSet.getNetwork();

            nnet.pauseLearning();                             // pauza
            imagePlaying(nnet);                                  //  racunanje odgovora mreze i pozivanje crtanja
            nnet.resumeLearning();                            //  nastavak ucenja
        }
    }

// DragNDrop - start
    // DnD liseneri
        class DTListener implements DropTargetListener
            {
                private DataFlavor chooseDropFlavor(DropTargetDropEvent e) {

                      DataFlavor chosen = null;

                      if (e.isDataFlavorSupported(TransferableObject.flavorN)) {
                        chosen = TransferableObject.flavorN;
                      } else if (e.isDataFlavorSupported(TransferableObject.flavorT)) {
                        chosen = TransferableObject.flavorT;
                      }else { }

                      return chosen;
                }


            @Override
                public void dragEnter(DropTargetDragEvent dtde) {
                    dtde.acceptDrag(dtde.getDropAction());
                }

            @Override
                public void dragExit(DropTargetEvent dte) {

                }

            @Override
                public void dragOver(DropTargetDragEvent dtde) {
                    dtde.acceptDrag(dtde.getDropAction());
                }

                public void dropActionChanged(DropTargetDragEvent dtde) {
                     dtde.acceptDrag(dtde.getDropAction());
                }
                 public void drop(DropTargetDropEvent e) {

                      DataFlavor chosen = chooseDropFlavor(e);
                      if (chosen == null) {
                        System.err.println( "No flavor match found" );
                        e.rejectDrop();
                        return;
                      }
                      // the actual operation
                      int da = e.getDropAction();
                      // the actions that the source has specified with DragGestureRecognizer
                      int sa = e.getSourceActions();
//                      System.out.println( "drop: sourceActions: " + sa);
//                      System.out.println( "drop: dropAction: " + da);

                      if ( ( sa & MultiLayerPerceptronSampleTopComponent.this.acceptableActions ) == 0 ) {
                            System.err.println( "No action match found" );
                            e.rejectDrop();
                            // ovde ispisati sta se radi u slucaju greske
                            //sourcePanel.setJtfMultylayer("ERROR");
                            JOptionPane.showMessageDialog(MultiLayerPerceptronSampleTopComponent.this, "GRESKA! NEDOZVOLJENA AKCIJA");
                            sourcePanel.clear();
                            return;
                      }
                    System.out.println("1");

                    Object data = null;

                        try {    // ovde ispisati sta se radi kod dropa

                            e.acceptDrop(MultiLayerPerceptronSampleTopComponent.this.acceptableActions);

                            data = e.getTransferable().getTransferData(chosen);
                            //System.out.println(chosen.toString());
                            //System.out.println("2 i data: " + data.toString());
                            if (data == null)   throw new NullPointerException();

                        } catch ( Throwable t ) {
                            System.err.println( "Couldn't get transfer data: " + t.getMessage());
                            t.printStackTrace();
                            e.dropComplete(false);
                            return;
                          }

                     //if (data instanceof NeuralNetwork ) {
                         if(data instanceof MultiLayerPerceptron){
                             NeuralNetwork dropNetwork = (MultiLayerPerceptron) data;
                             int inputNeurons = dropNetwork.getInputNeurons().length;
                             int outputNeurons = dropNetwork.getOutputNeurons().length;
                             //System.out.println("3.1 uspeli smo i ulaznih je: "+ inputNeurons +" a izlaznih je: "+outputNeurons);

                             if(inputNeurons == 2 && outputNeurons == 1)
                             {
                                 int i = 0;
                                 String midleLayers = "";
                                 //posto su sve provere prosle krecemo sa crtanjem i ubacivanjem
                                 for (Layer l : dropNetwork.getLayers()) {
//                                     Layer l = it.next();

                                     if(i != 0 && i != dropNetwork.getLayersCount()-1){
                                        if(i>1) midleLayers = midleLayers + " ";
                                        midleLayers = midleLayers+(l.getNeuronsCount()-1);}
                                    i++;

                                 }
                                 sourcePanel.setJtfMultylayer(midleLayers);

                                 //crtanje
                                 imagePlaying(dropNetwork);

                                 //ovde treba ostale podatke izvuci
                                 sourcePanel.setJTFs("", "", "");
                             }
                             else{
                                 //sourcePanel.setJtfMultylayer("ERROR");
                                 JOptionPane.showMessageDialog(MultiLayerPerceptronSampleTopComponent.this, "Error! MREZA MORA BITI 2 X 1");
                                 sourcePanel.clear();}


                     } else if (data instanceof DataSet) {
                         DataSet dropTrainingSet = (DataSet) data;
                         DataSetRow element = dropTrainingSet.getRowAt(0);
                         if(element.getDesiredOutput().length==1 && element.getInput().length==2){
                            // System.out.println("Training ulazi");
                         }
                           //dropTrainingSet.trainingElements().get(0).
                           //if(dropTrainingSet.trainingElements().firstElement().getInput().size()==2 && dropTrainingSet.trainingElements().firstElement().get.size()==2)
                         inputSpacePanel.drawPointsFromTrainingSet(dropTrainingSet);
                     }

                     else JOptionPane.showMessageDialog(MultiLayerPerceptronSampleTopComponent.this, "Error");

                   e.dropComplete(true);
                }

            }

    private DropTarget dropTarget;
    private DropTarget dropTarget2;
    private DropTarget dropTarget3;
    private DropTargetListener dtListener;
    private int acceptableActions = DnDConstants.ACTION_COPY;
// DragNDrop - end
}
