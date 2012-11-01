/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rpgslickport;

import java.util.Random;

/**
 *
 * @author redblast71
 */
public class CharacterRuby {
    
  /*public int id;                    // ID
  public float x;                   // map x-coordinate (logical)
  public float y;                    // map y-coordinate (logical)
  public float dx;                   // deltaX
  public float dy;                    // deltaY
  public int tile_id;                 // tile ID (invalid if 0)
  public String character_name;          // character graphic filename
  public int character_index;         // character graphic index
  public float opacity;                 // opacity level
  public int blend_type;               // blending method
  public int direction;                // direction
  public int pattern;                  // pattern
  public boolean move_route_forcing;       // forced move route flag
  public int priority_type;            // priority type
  public boolean through;                  // pass-through
  public int bush_depth;               // bush depth
  private int jump_count;
  private int original_direction;               // Original direction
  private int original_pattern;                 // Original pattern
  private int move_type;                        // Movement type
  private int move_speed;                       // Movement speed
  private int move_frequency;                   // Movement frequency
  private int[] move_route;                     // Move route
  private int move_route_index;                 // Move route index
  private int[] original_move_route;            // Original move route
  private int original_move_route_index;        // Original move route index
  private boolean walk_anime;                    // Walking animation
  private boolean step_anime;                   // Stepping animation
  private boolean direction_fix;               // Fixed direction
  private int anime_count;                     // Animation count
  private int stop_count;                     // Stop count
  private int jump_peak;                        // Jump peak count
  private int wait_count;                      // Wait count
  private boolean locked;                     // Locked flag
  private int prelock_direction;               // Direction before lock
  private boolean move_failed;                  // Movement failed flag
  public static int animation_id;             // animation ID
  public static int balloon_id;               // balloon icon ID
  public static boolean transparent;              // transparency flag
  //--------------------------------------------------------------------------
  // * Object Initialization
  //--------------------------------------------------------------------------
  public void initialize(){
    id = 0;
    x = 0;
    y = 0;
    tile_id = 0;
    character_name = "";
    character_index = 0;
    opacity = 255;
    blend_type = 0;
    direction = 2;
    pattern = 1;
    move_route_forcing = false;
    priority_type = 1;
    through = false;
    bush_depth = 0;
    animation_id = 0;
    balloon_id = 0;
    transparent = false;
    original_direction = 2;               // Original direction
    original_pattern = 1;                 // Original pattern
    move_type = 0;                        // Movement type
    move_speed = 4;                       // Movement speed
    move_frequency = 6;                   // Movement frequency
    move_route = null;                     // Move route
    move_route_index = 0;                 // Move route index
    original_move_route = null;            // Original move route
    original_move_route_index = 0;        // Original move route index
    walk_anime = true;                    // Walking animation
    step_anime = false;                   // Stepping animation
    direction_fix = false;                // Fixed direction
    anime_count = 0;                      // Animation count
    stop_count = 0;                       // Stop count
    jump_peak = 0;                        // Jump peak count
    wait_count = 0;                       // Wait count
    locked = false;                       // Locked flag
    prelock_direction = 0;                // Direction before lock
    move_failed = false;                  // Movement failed flag
  }
  //--------------------------------------------------------------------------
  // * Determine if Moving
  //    Compare with logical coordinates.
  //--------------------------------------------------------------------------
  public boolean isMoving(){
    return ((dx != 0) || (dy != 0));
  }
  //--------------------------------------------------------------------------
  // * Determine if Jumping
  //--------------------------------------------------------------------------
  public boolean isJumping(){
    return jump_count > 0;
  }
  //--------------------------------------------------------------------------
  // * Determine if Stopping
  //--------------------------------------------------------------------------
  public boolean isStopping(){
    return (!(isMoving()||isJumping()));
  }
  //--------------------------------------------------------------------------
  // * Determine if Dashing
  //--------------------------------------------------------------------------
  public boolean isDashing(){
    return false;
  }
  //--------------------------------------------------------------------------
  // * Determine if Debug Pass-through State
  //--------------------------------------------------------------------------
  public boolean debug_through(){
    return false;
  }
  //--------------------------------------------------------------------------
  // * Straighten Position
  //--------------------------------------------------------------------------
  public void straighten(){
    if(walk_anime || step_anime){
    pattern = 1;
    anime_count = 0;
    }
  }
  //--------------------------------------------------------------------------
  // * Force Move Route
  //     move_route  new move route
  //--------------------------------------------------------------------------
  public void force_move_route(int[] move_route1){
    if(original_move_route == null){
      original_move_route = move_route;
      original_move_route_index = move_route_index;
    }
    move_route = move_route1;
    move_route_index = 0;
    move_route_forcing = true;
    prelock_direction = 0;
    wait_count = 0;
    move_type_custom();
  }
  //--------------------------------------------------------------------------
  // * Determine Coordinate Match
  //     x  x-coordinate
  //     y  y-coordinate
  //--------------------------------------------------------------------------
  public boolean isPos(float x1,float y1){
    return (x1 == x && y1 == y);
  }
  //--------------------------------------------------------------------------
  // * Coordinate Match and "Passage OFF" Determination (nt = No Through)
  //     x  x-coordinate
  //     y  y-coordinate
  //--------------------------------------------------------------------------
  public boolean isPosAndNT(float x, float y){
    return (isPos(x, y) && !through);
  }
  //--------------------------------------------------------------------------
  // * Determine if Passable
  //     x  x-coordinate
  //     y  y-coordinate
  //--------------------------------------------------------------------------
  
  public void isBlocked(){
      // Passable
  }
  //--------------------------------------------------------------------------
  // * Determine if Map is Passable
  //     x  x-coordinate
  //     y  y-coordinate
  //    Gets whether the tile at the designated coordinates is passable.
  //--------------------------------------------------------------------------
  public boolean blocked(){
    return true;
  }

  //--------------------------------------------------------------------------
  // * Lock (process for stopping event in progress)
  //--------------------------------------------------------------------------
  public void lock(){
    if(!locked){
      prelock_direction = direction;
      turn_toward_player();
      locked = true;
    }
  }
  //--------------------------------------------------------------------------
  // * Unlock
  //--------------------------------------------------------------------------
  public void unlock(){
    if(locked){
      locked = false;
      set_direction(prelock_direction);
    }
  }
  //--------------------------------------------------------------------------
  // * Move to Designated Position
  //     x  x-coordinate
  //     y  y-coordinate
  //--------------------------------------------------------------------------
  
  public void moveto(int x1, int y1){
    x = x1 % map.width;
    y = y1 % map.height;
    prelock_direction = 0;
    straighten();
    update_bush_depth();
  }
  //--------------------------------------------------------------------------
  // * Change Direction to Designated Direction
  //     direction  Direction
  //--------------------------------------------------------------------------
  public void set_direction(int direction1){
    if(!direction_fix && direction != 0){
      direction = direction1;
      stop_count = 0;
    }
  }
  //--------------------------------------------------------------------------
  // * Determine Object Type
  //--------------------------------------------------------------------------
  public boolean isObject(){
    return (tile_id > 0 || character_name.charAt(0) == '!');
  }
  //--------------------------------------------------------------------------
  // * Get Screen X-Coordinates
  //--------------------------------------------------------------------------
  public void screen_x(){

  }
  //--------------------------------------------------------------------------
  // * Get Screen Y-Coordinates
  //--------------------------------------------------------------------------
  public void screen_y(){
  
  }
  //--------------------------------------------------------------------------
  // * Get Screen Z-Coordinates
  //--------------------------------------------------------------------------
  public void screen_z(){

  }
  //--------------------------------------------------------------------------
  // * Frame Update
  //--------------------------------------------------------------------------
  public void update(){
    if(isJumping()){                 // Jumping
      update_jump();
    }
    else if(isMoving()){             // Moving
      update_move();
    }
    else{                       // Stopped
      update_stop();
    }
    if(wait_count > 0){          // Waiting
      wait_count -= 1;
    }
      else if (move_route_forcing){   // Forced move route
      move_type_custom();
    }
      else if (!locked){           // Not locked
      update_self_movement();
    }
    update_animation();
  }
  //--------------------------------------------------------------------------
  // * Update While Jumping
  //--------------------------------------------------------------------------
  public void update_jump(){
    jump_count -= 1;
    update_bush_depth();
  }
  //--------------------------------------------------------------------------
  // * Update While Moving
  //--------------------------------------------------------------------------
  public void update_move(){
    distance = Math.pow(2, move_speed);   // Convert to movement distance
    if(isDashing()){
    distance *= 2; 
    } // If dashing, double it
    if(!isMoving()){
    update_bush_depth();
    }
    if (walk_anime){
      anime_count += 1.5;
    }
    else if (step_anime){
      anime_count += 1;
    }
  }
  //--------------------------------------------------------------------------
  // * Update While Stopped
  //--------------------------------------------------------------------------
  public void update_stop(){
    if(step_anime){
      anime_count += 1;
    }
      else if (pattern != original_pattern){
      anime_count += 1.5;
    }
    if(!locked){
    stop_count += 1;
    }
  }
  //--------------------------------------------------------------------------
  // * Update During Self movement
  //--------------------------------------------------------------------------
  public void update_self_movement(){
    if(stop_count > 30 * (5 - move_frequency)){
      switch(move_type){
      case 1:  move_type_random();
      case 2:  move_type_toward_player();
      case 3:  move_type_custom();
      }
    }
  }
  //--------------------------------------------------------------------------
  // * Update Animation Count
  //--------------------------------------------------------------------------
  public void update_animation(){
    int speed = move_speed + (isDashing() ? 1:  0);
    if(anime_count > 18 - speed * 2){
      if(!step_anime && stop_count > 0){
        pattern = original_pattern;
        }
        else{
        pattern = (pattern + 1) % 4;
      }
      anime_count = 0;
    }
  }
  //--------------------------------------------------------------------------
  // * Update Bush Depth
  //--------------------------------------------------------------------------
  public void update_bush_depth(){
    if(isObject() || priority_type != 1 || jump_count > 0){
      bush_depth = 0;
    }
    else{/*
      bush = $game_map.bush?(@x, @y)
      if bush and not moving?
        @bush_depth = 8
      elsif not bush
        @bush_depth = 0
      }
    }
  //--------------------------------------------------------------------------
  // * Move Type  Random
  //--------------------------------------------------------------------------

  public void move_type_random(){
      Random r = new Random();
    switch(r.nextInt(6)){
    case 0: move_random();
    case 1: move_random();
    case 2: move_forward();
    case 3: move_forward();
    case 4: move_forward();
    case 5: stop_count = 0;
    }
  }
  //--------------------------------------------------------------------------
  // * Move Type  Approach
  //--------------------------------------------------------------------------
  public void move_type_toward_player(){
      Random r = new Random();
    int sx = x - player.x;
    int sy = y - player.y;
    if(Math.abs(sx) + Math.abs(sy) >= 20){
      move_random();
    }
    else{
      switch(r.nextInt(6)){
      case 0:  move_toward_player();
      case 1:  move_toward_player();
      case 2:  move_toward_player();
      case 3:  move_toward_player();
      case 4:  move_random();
      case 5:  move_forward();
      }
    }
  }
  //--------------------------------------------------------------------------
  // * Move Type  Custom
  //--------------------------------------------------------------------------
  public void move_type_custom(){
    if stopping?
      command = @move_route.list[@move_route_index]   // Get movement command
      @move_failed = false
      if command.code == 0                            // End of list
        if @move_route.repeat                         // [Repeat Action]
          @move_route_index = 0
        elsif @move_route_forcing                     // Forced move route
          @move_route_forcing = false                 // Cancel forcing
          @move_route = @original_move_route          // Restore original
          @move_route_index = @original_move_route_index
          @original_move_route = null
        }
      else
        case command.code
        when 1    // Move Down
          move_down
        when 2    // Move Left
          move_left
        when 3    // Move Right
          move_right
        when 4    // Move Up
          move_up
        when 5    // Move Lower Left
          move_lower_left
        when 6    // Move Lower Right
          move_lower_right
        when 7    // Move Upper Left
          move_upper_left
        when 8    // Move Upper Right
          move_upper_right
        when 9    // Move at Random
          move_random
        when 10   // Move toward Player
          move_toward_player
        when 11   // Move away from Player
          move_away_from_player
        when 12   // 1 Step Forward
          move_forward
        when 13   // 1 Step Backwards
          move_backward
        when 14   // Jump
          jump(command.parameters[0], command.parameters[1])
        when 15   // Wait
          @wait_count = command.parameters[0] - 1
        when 16   // Turn Down
          turn_down
        when 17   // Turn Left
          turn_left
        when 18   // Turn Right
          turn_right
        when 19   // Turn Up
          turn_up
        when 20   // Turn 90° Right
          turn_right_90
        when 21   // Turn 90° Left
          turn_left_90
        when 22   // Turn 180°
          turn_180
        when 23   // Turn 90° Right or Left
          turn_right_or_left_90
        when 24   // Turn at Random
          turn_random
        when 25   // Turn toward Player
          turn_toward_player
        when 26   // Turn away from Player
          turn_away_from_player
        when 27   // Switch ON
          $game_switches[command.parameters[0]] = true
          $game_map.need_refresh = true
        when 28   // Switch OFF
          $game_switches[command.parameters[0]] = false
          $game_map.need_refresh = true
        when 29   // Change Speed
          @move_speed = command.parameters[0]
        when 30   // Change Frequency
          @move_frequency = command.parameters[0]
        when 31   // Walking Animation ON
          @walk_anime = true
        when 32   // Walking Animation OFF
          @walk_anime = false
        when 33   // Stepping Animation ON
          @step_anime = true
        when 34   // Stepping Animation OFF
          @step_anime = false
        when 35   // Direction Fix ON
          @direction_fix = true
        when 36   // Direction Fix OFF
          @direction_fix = false
        when 37   // Through ON
          @through = true
        when 38   // Through OFF
          @through = false
        when 39   // Transparent ON
          @transparent = true
        when 40   // Transparent OFF
          @transparent = false
        when 41   // Change Graphic
          set_graphic(command.parameters[0], command.parameters[1])
        when 42   // Change Opacity
          @opacity = command.parameters[0]
        when 43   // Change Blending
          @blend_type = command.parameters[0]
        when 44   // Play SE
          command.parameters[0].play
        when 45   // Script
          eval(command.parameters[0])
        }
        if not @move_route.skippable and @move_failed
          return  // [Skip if Cannot Move] OFF & movement failure
        }
        @move_route_index += 1
      }
    }
  }
  //--------------------------------------------------------------------------
  // * Increase Steps
  //--------------------------------------------------------------------------
  public void increase_steps(){
    stop_count = 0;
    update_bush_depth();
  }
  //--------------------------------------------------------------------------
  // * Calculate X Distance From Player
  //--------------------------------------------------------------------------
  public void distance_x_from_player
    sx = @x - $game_player.x
    if $game_map.loop_horizontal?         // When looping horizontally
      if sx.abs > $game_map.width / 2     // Larger than half the map width?
        sx -= $game_map.width             // Subtract map width
      }
    }
    return sx
  }
  //--------------------------------------------------------------------------
  // * Calculate Y Distance From Player
  //--------------------------------------------------------------------------
  public void distance_y_from_player
    sy = @y - $game_player.y
    if $game_map.loop_vertical?           // When looping vertically
      if sy.abs > $game_map.height / 2    // Larger than half the map height?
        sy -= $game_map.height            // Subtract map height
      }
    }
    return sy
  }
  //--------------------------------------------------------------------------
  // * Move Down
  //     turn_ok  Allows change of direction on the spot
  //--------------------------------------------------------------------------
  public void move_down(turn_ok = true)
    if passable?(@x, @y+1)                  // Passable
      turn_down
      @y = $game_map.round_y(@y+1)
      @real_y = (@y-1)*256
      increase_steps
      @move_failed = false
    else                                    // Impassable
      turn_down if turn_ok
      check_event_trigger_touch(@x, @y+1)   // Touch event is triggered?
      @move_failed = true
    }
  }
  //--------------------------------------------------------------------------
  // * Move Left
  //     turn_ok  Allows change of direction on the spot
  //--------------------------------------------------------------------------
  public void move_left(turn_ok = true)
    if passable?(@x-1, @y)                  // Passable
      turn_left
      @x = $game_map.round_x(@x-1)
      @real_x = (@x+1)*256
      increase_steps
      @move_failed = false
    else                                    // Impassable
      turn_left if turn_ok
      check_event_trigger_touch(@x-1, @y)   // Touch event is triggered?
      @move_failed = true
    }
  }
  //--------------------------------------------------------------------------
  // * Move Right
  //     turn_ok  Allows change of direction on the spot
  //--------------------------------------------------------------------------
  public void move_right(turn_ok = true)
    if passable?(@x+1, @y)                  // Passable
      turn_right
      @x = $game_map.round_x(@x+1)
      @real_x = (@x-1)*256
      increase_steps
      @move_failed = false
    else                                    // Impassable
      turn_right if turn_ok
      check_event_trigger_touch(@x+1, @y)   // Touch event is triggered?
      @move_failed = true
    }
  }
  //--------------------------------------------------------------------------
  // * Move up
  //     turn_ok  Allows change of direction on the spot
  //--------------------------------------------------------------------------
  public void move_up(turn_ok = true)
    if passable?(@x, @y-1)                  // Passable
      turn_up
      @y = $game_map.round_y(@y-1)
      @real_y = (@y+1)*256
      increase_steps
      @move_failed = false
    else                                    // Impassable
      turn_up if turn_ok
      check_event_trigger_touch(@x, @y-1)   // Touch event is triggered?
      @move_failed = true
    }
  }
  //--------------------------------------------------------------------------
  // * Move Lower Left
  //--------------------------------------------------------------------------
  public void move_lower_left
    unless @direction_fix
      @direction = (@direction == 6 ? 4  @direction == 8 ? 2  @direction)
    }
    if (passable?(@x, @y+1) and passable?(@x-1, @y+1)) or
       (passable?(@x-1, @y) and passable?(@x-1, @y+1))
      @x -= 1
      @y += 1
      increase_steps
      @move_failed = false
    else
      @move_failed = true
    }
  }
  //--------------------------------------------------------------------------
  // * Move Lower Right
  //--------------------------------------------------------------------------
  public void move_lower_right
    unless @direction_fix
      @direction = (@direction == 4 ? 6  @direction == 8 ? 2  @direction)
    }
    if (passable?(@x, @y+1) and passable?(@x+1, @y+1)) or
       (passable?(@x+1, @y) and passable?(@x+1, @y+1))
      @x += 1
      @y += 1
      increase_steps
      @move_failed = false
    else
      @move_failed = true
    }
  }
  //--------------------------------------------------------------------------
  // * Move Upper Left
  //--------------------------------------------------------------------------
  public void move_upper_left
    unless @direction_fix
      @direction = (@direction == 6 ? 4  @direction == 2 ? 8  @direction)
    }
    if (passable?(@x, @y-1) and passable?(@x-1, @y-1)) or
       (passable?(@x-1, @y) and passable?(@x-1, @y-1))
      @x -= 1
      @y -= 1
      increase_steps
      @move_failed = false
    else
      @move_failed = true
    }
  }
  //--------------------------------------------------------------------------
  // * Move Upper Right
  //--------------------------------------------------------------------------
  public void move_upper_right
    unless @direction_fix
      @direction = (@direction == 4 ? 6  @direction == 2 ? 8  @direction)
    }
    if (passable?(@x, @y-1) and passable?(@x+1, @y-1)) or
       (passable?(@x+1, @y) and passable?(@x+1, @y-1))
      @x += 1
      @y -= 1
      increase_steps
      @move_failed = false
    else
      @move_failed = true
    }
  }
  //--------------------------------------------------------------------------
  // * Move at Random
  //--------------------------------------------------------------------------
  public void move_random
    case rand(4)
    when 0;  move_down(false)
    when 1;  move_left(false)
    when 2;  move_right(false)
    when 3;  move_up(false)
    }
  }
  //--------------------------------------------------------------------------
  // * Move toward Player
  //--------------------------------------------------------------------------
  public void move_toward_player
    sx = distance_x_from_player
    sy = distance_y_from_player
    if sx != 0 or sy != 0
      if sx.abs > sy.abs                  // Horizontal distance is longer
        sx > 0 ? move_left  move_right   // Prioritize left-right
        if @move_failed and sy != 0
          sy > 0 ? move_up  move_down
        }
      else                                // Vertical distance is longer
        sy > 0 ? move_up  move_down      // Prioritize up-down
        if @move_failed and sx != 0
          sx > 0 ? move_left  move_right
        }
      }
    }
  }
  //--------------------------------------------------------------------------
  // * Move away from Player
  //--------------------------------------------------------------------------
  public void move_away_from_player
    sx = distance_x_from_player
    sy = distance_y_from_player
    if sx != 0 or sy != 0
      if sx.abs > sy.abs                  // Horizontal distance is longer
        sx > 0 ? move_right  move_left   // Prioritize left-right
        if @move_failed and sy != 0
          sy > 0 ? move_down  move_up
        }
      else                                // Vertical distance is longer
        sy > 0 ? move_down  move_up      // Prioritize up-down
        if @move_failed and sx != 0
          sx > 0 ? move_right  move_left
        }
      }
    }
  }
  //--------------------------------------------------------------------------
  // * 1 Step Forward
  //--------------------------------------------------------------------------
  public void move_forward(){
    switch(direction){
    case 2;  move_down(false)
    case 4;  move_left(false)
    case 6;  move_right(false)
    case 8;  move_up(false)
    }
  }
  //--------------------------------------------------------------------------
  // * 1 Step Backward
  //--------------------------------------------------------------------------
  public void move_backward
    last_direction_fix = @direction_fix
    @direction_fix = true
    case @direction
    when 2;  move_up(false)
    when 4;  move_right(false)
    when 6;  move_left(false)
    when 8;  move_down(false)
    }
    @direction_fix = last_direction_fix
  }
  //--------------------------------------------------------------------------
  // * Jump
  //     x_plus  x-coordinate plus value
  //     y_plus  y-coordinate plus value
  //--------------------------------------------------------------------------
  public void jump(x_plus, y_plus){
    if(Math.abs(x_plus) > y_plus.abs){            // Horizontal distance is longer
      x_plus < 0 ? turn_left  turn_right
    elsif x_plus.abs > y_plus.abs         // Vertical distance is longer
      y_plus < 0 ? turn_up  turn_down
    }
    @x += x_plus
    @y += y_plus
    distance = Math.sqrt(x_plus * x_plus + y_plus * y_plus).round
    @jump_peak = 10 + distance - @move_speed
    @jump_count = @jump_peak * 2
    @stop_count = 0
    straighten();
  }
  //--------------------------------------------------------------------------
  // * Turn Down
  //--------------------------------------------------------------------------
  public void turn_down
    set_direction(2)
  }
  //--------------------------------------------------------------------------
  // * Turn Left
  //--------------------------------------------------------------------------
  public void turn_left
    set_direction(4)
  }
  //--------------------------------------------------------------------------
  // * Turn Right
  //--------------------------------------------------------------------------
  public void turn_right
    set_direction(6)
  }
  //--------------------------------------------------------------------------
  // * Turn Up
  //--------------------------------------------------------------------------
  public void turn_up
    set_direction(8)
  }
  //--------------------------------------------------------------------------
  // * Turn 90° Right
  //--------------------------------------------------------------------------
  public void turn_right_90
    case @direction
    when 2;  turn_left
    when 4;  turn_up
    when 6;  turn_down
    when 8;  turn_right
    }
  }
  //--------------------------------------------------------------------------
  // * Turn 90° Left
  //--------------------------------------------------------------------------
  public void turn_left_90
    case @direction
    when 2;  turn_right
    when 4;  turn_down
    when 6;  turn_up
    when 8;  turn_left
    }
  }
  //--------------------------------------------------------------------------
  // * Turn 180°
  //--------------------------------------------------------------------------
  public void turn_180
    case @direction
    when 2;  turn_up
    when 4;  turn_right
    when 6;  turn_left
    when 8;  turn_down
    }
  }
  //--------------------------------------------------------------------------
  // * Turn 90° Right or Left
  //--------------------------------------------------------------------------
  public void turn_right_or_left_90
    case rand(2)
    when 0;  turn_right_90
    when 1;  turn_left_90
    }
  }
  //--------------------------------------------------------------------------
  // * Turn at Random
  //--------------------------------------------------------------------------
  public void turn_random
    case rand(4)
    when 0;  turn_up
    when 1;  turn_right
    when 2;  turn_left
    when 3;  turn_down
    }
  }
  //--------------------------------------------------------------------------
  // * Turn toward Player
  //--------------------------------------------------------------------------
  public void turn_toward_player
    sx = distance_x_from_player
    sy = distance_y_from_player
    if sx.abs > sy.abs                    // Horizontal distance is longer
      sx > 0 ? turn_left  turn_right
    elsif sx.abs < sy.abs                 // Vertical distance is longer
      sy > 0 ? turn_up  turn_down
    }
  }
  //--------------------------------------------------------------------------
  // * Turn away from Player
  //--------------------------------------------------------------------------
  public void turn_away_from_player
    sx = distance_x_from_player
    sy = distance_y_from_player
    if sx.abs > sy.abs                    // Horizontal distance is longer
      sx > 0 ? turn_right  turn_left
    elsif sx.abs < sy.abs                 // Vertical distance is longer
      sy > 0 ? turn_down  turn_up
    }
  }
  //--------------------------------------------------------------------------
  // * Change Graphic
  //     character_name   new character graphic filename
  //     character_index  new character graphic index
  //--------------------------------------------------------------------------
  public void set_graphic(character_name, character_index)
    @tile_id = 0
    @character_name = character_name
    @character_index = character_index
  }
}
*/
    
}
