import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class WindowDemo extends JFrame implements ActionListener, MouseListener
{
    // gui components that are contained in this frame:
    private JPanel topPanel, bottomPanel;    // top and bottom panels in the main window
    private JLabel instructionLabel; 
    private JLabel moveset;// a text label to tell the user what to do
    private JLabel infoLabel;            // a text label to show the coordinate of the selected square
    private JButton topButton;                // a 'reset' button to appear in the top panel
    private GridSquare [][] gridSquares;    // squares to appear in grid formation in the bottom panel
    private int rows,columns;
    private int a= -99;
    private int b= -99;
    private int c = -99;
    private int d = -99;
    private int k;
    private int m;
    private int target;
    private int count = 0;// the size of the grid
    private String Coord;
    private String move;
    private Object previous;
    private List<String> travel = new ArrayList<>();
    private List<String> moves = new ArrayList<>();
    public GridSquare triangle;

    public WindowDemo(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;
        this.setSize(600,600);
        target = (rows)*(columns);
        
        // first create the panels
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(rows, columns));
        bottomPanel.setSize(500,500);
        
        // then create the components for each panel and add them to it
        
        // for the top panel:
        instructionLabel = new JLabel("Click the Squares!");
        moveset = new JLabel("Possible moves");
        infoLabel = new JLabel("No square clicked yet.");
        topButton = new JButton("Reset");
        topButton.addActionListener(this);            // IMPORTANT! Without this, clicking the square does nothing.
        
        topPanel.add(instructionLabel);
        topPanel.add(moveset);
        topPanel.add (topButton);
        topPanel.add(infoLabel);
        
    
        // for the bottom panel:    
        // create the squares and add them to the grid
        gridSquares = new GridSquare[rows][columns];
        for ( int x = 0; x < columns; x ++)
        {
            for ( int y = 0; y < rows; y ++)
            {
                gridSquares[x][y] = new GridSquare(x, y);
                gridSquares[x][y].setSize(20, 20);
                gridSquares[x][y].setColor(x + y);
                
                gridSquares[x][y].addMouseListener(this);        // AGAIN, don't forget this line!
                
                bottomPanel.add(gridSquares[x][y]);
            }
        }
        
        // now add the top and bottom panels to the main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);        // needs to be center or will draw too small
        
        // housekeeping : behaviour
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    
    /*
     *  handles actions performed in the gui
     *  this method must be present to correctly implement the ActionListener interface
     */
    public void actionPerformed(ActionEvent aevt)
    {
        // get the object that was selected in the gui
        Object selected = aevt.getSource();
                
        // if resetting the squares' colours is requested then do so
        if ( selected.equals(topButton) )
        {
            for ( int x = 0; x < columns; x ++)
            {
                for ( int y = 0; y < rows; y ++)
                {
                    gridSquares [x][y].setColor(x + y);
                }
            }
            count = 0;
            moves.clear();
            travel.clear();
            instructionLabel.setText("Count: "+Integer.toString(count)+"  ");
            a = -99;
            b = -99;
        }
    }


    // Mouse Listener events
    public void mouseClicked(MouseEvent mevt)
    {
        // get the object that was selected in the gui
        Object selected = mevt.getSource();
        
        // if a gridsquare is selected then switch its color
        if (selected instanceof GridSquare)
        {
            GridSquare square = (GridSquare) selected;
            int x = square.getXcoord();
            int y = square.getYcoord();
            Coord = "(" + Integer.toString(x)+","+Integer.toString(y)+")";
            movelist(x,y);
            if ((((x ==2+a||x==a-2)&(y==b+1||y==b-1))||((x ==1+a||x==a-1)&(y==b+2||y==b-2))||((a==-99)||(b==-99)))){
                if(!(travel.contains(Coord))){
                    if(moves.isEmpty()){square.switchColor(); count++; gridSquares[k][m].setBackground(Color.blue);
                        if (count==target){instructionLabel.setText("GAME WON");}
                        else{instructionLabel.setText("GAME OVER");}}
                    else{
                        square.switchColor();
                        infoLabel.setText("("+x+","+y+ ")"+ " last selected.");
                        travel.add("(" + Integer.toString(x)+","+Integer.toString(y)+")");
                        a = x;
                        b = y;
                        count = count+1;
                        instructionLabel.setText("Count: "+Integer.toString(count)+"  ");
                        if(!(count==1)){
                        triangle = new GridSquare(k,m);
                        triangle.setBackground(Color.blue);
                        gridSquares[k][m].setBackground(Color.blue);}
                        k = x;
                        m = y;
                        }                   
                    }
            else{instructionLabel.setText("ALREADY TRAVELLED");}}
          else {
                    instructionLabel.setText("WRONG BUTTON");
         } 
        }
    }
    
    public List movelist(int x, int y){
          moves.clear();
          c = x+2;
          d = y+1;
          move = "(" + Integer.toString(c)+","+Integer.toString(d)+")";
          if (!(travel.contains(move))&((c<rows)&(d<columns)&(0<=c)&(0<=d))){
              moves.add(move);
          }
          c = x+2;
          d = y-1;
          move = "(" + Integer.toString(c)+","+Integer.toString(d)+")";
          if (!(travel.contains(move))&((c<rows)&(d<columns)&(0<=c)&(0<=d))){
              moves.add(move);
          }
          c = x+1;
          d = y+2;
          move = "(" + Integer.toString(c)+","+Integer.toString(d)+")";
          if (!(travel.contains(move))&((c<rows)&(d<columns)&(0<=c)&(0<=d))){
              moves.add(move);
          }
          c = x+1;
          d = y-2;
          move = "(" + Integer.toString(c)+","+Integer.toString(d)+")";
          if (!(travel.contains(move))&((c<rows)&(d<columns)&(0<=c)&(0<=d))){
              moves.add(move);
          }
          c = x-2;
          d = y+1;
          move = "(" + Integer.toString(c)+","+Integer.toString(d)+")";
          if (!(travel.contains(move))&((c<rows)&(d<columns)&(0<=c)&(0<=d))){
              moves.add(move);
          }
          c = x-2;
          d = y-1;
          move = "(" + Integer.toString(c)+","+Integer.toString(d)+")";
          if (!(travel.contains(move))&((c<rows)&(d<columns)&(0<=c)&(0<=d))){
              moves.add(move);
          }
          c = x-1;
          d = y-2;
          move = "(" + Integer.toString(c)+","+Integer.toString(d)+")";
          if (!(travel.contains(move))&((c<rows)&(d<columns)&(0<=c)&(0<=d))){
              moves.add(move);
          }
          c = x-1;
          d = y+2;
          move = "(" + Integer.toString(c)+","+Integer.toString(d)+")";
          if (!(travel.contains(move))&((c<rows)&(d<columns)&(0<=c)&(0<=d))){
              moves.add(move);
          }
          String listString = String.join(", ", moves);
          moveset.setText("Moves :" +listString);
          return moves;
    }

    // not used but must be present to fulfil MouseListener contract
    public void mouseEntered(MouseEvent arg0){}
    public void mouseExited(MouseEvent arg0) {}
    public void mousePressed(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
}
