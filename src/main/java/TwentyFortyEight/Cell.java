package TwentyFortyEight;

public class Cell {

    private int x;
    private int y;
    private int value;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }

    public void place() {
        if (this.value == 0) {
            this.value = (App.random.nextInt(2) + 1) * 2;
        }
    }

    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    

    /**
     * This draws the cell
     */
    public void draw(App app) {
        app.stroke(156, 139, 124);
        
        boolean isHovered = app.mouseX > x * App.CELLSIZE && app.mouseX < (x + 1) * App.CELLSIZE
                && app.mouseY > y * App.CELLSIZE && app.mouseY < (y + 1) * App.CELLSIZE;
        
        // Set the fill color based on the tile value
        if (isHovered) {
            app.fill(232, 207, 184); // hover color
        } else if (this.value == 2) {
            app.fill(238, 228, 218); // color for value 2
        } else if (this.value == 4) {
            app.fill(237, 224, 200); // color for value 4
        } else if (this.value == 8) {
            app.fill(241,177,120); // color for value 4
        }else if (this.value == 16) {
            app.fill(245,149,101); // color for value 4
        }else if (this.value == 32) {
            app.fill(242,126,94); // color for value 4
        }else if (this.value == 64) {
            app.fill(244,94,59); // color for value 4
        }else if (this.value == 128) {
            app.fill(237,206,115); // color for value 4
        }else if (this.value == 256) {
            app.fill(237,204,99); // color for value 4
        }else if (this.value == 512) {
            app.fill(236,199,81); // color for value 4
        }else if (this.value == 1024) {
            app.fill(238,197,63); // color for value 4
        }else if (this.value == 2048) {
            app.fill(238,194,45); // color for value 4
        }else if (this.value == 0) {
            app.fill(205, 193, 180); // empty tile
        } else {
            app.fill(189, 172, 151); // default tile
        }
        
        // Draw the tile
        app.rect(x * App.CELLSIZE, y * App.CELLSIZE, App.CELLSIZE, App.CELLSIZE);
        
        // Draw the number if value > 0
        if (this.value > 0) {
            app.fill(0); // black text
            
            // Calculate text position based on number of digits
            String valueText = String.valueOf(this.value);
            float xPos, yPos;
            
            // Center position
            xPos = (x + 0.5f) * App.CELLSIZE;
            yPos = (y + 0.6f) * App.CELLSIZE;
            
            // Adjust text alignment
            app.textAlign(app.CENTER, app.CENTER);
            
            // Adjust text size based on number of digits
            if (valueText.length() >= 4) {
                app.textSize(32); // Smaller text for 4+ digit numbers
            } else if (valueText.length() == 3) {
                app.textSize(36); // Medium text for 3-digit numbers
            } else {
                app.textSize(40); // Default text size for 1-2 digit numbers
            }
            
            // Draw the text
            app.text(valueText, xPos, yPos);
            
            // Reset text size to default
            app.textSize(40);
        }
    }
    

}