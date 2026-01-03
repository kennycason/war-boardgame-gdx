# Movement Diversity Brainstorming

## Current Movement Analysis

### Current Patterns (Too Similar):
- **Bomber**: 1-4 horizontal/vertical (move + attack)
- **Tank**: 1-2 horizontal/vertical (move + attack)
- **Commander**: 1 tile any direction (move + attack)
- **Artillery**: 1 tile any direction (move), 2-3 horizontal/vertical (attack)
- **Missile**: 1 tile any direction (move), 2-5 diagonal (attack, single-use)
- **Air Defense**: 1 tile any direction (move only)
- **Infantry**: 1 horizontal/vertical (move), 1 diagonal (attack) ✅ **GOOD DIVERSITY**

### Problem:
Too many pieces use horizontal/vertical movement. Need more L-shapes, pure diagonal, and complementary patterns.

---

## SOLID Movement Type Ideas

### 1. **Cavalry / Horse** (L-Shape Movement)
**Movement Pattern**: Classic chess knight move
- **Move**: L-shape (2 tiles one direction, 1 tile perpendicular)
- **Attack**: Same as movement
- **Special**: Can jump over pieces (friendly and enemy)
- **Range**: Exactly 2+1 pattern (8 possible destinations)
- **Why It Works**: 
  - Completely different from existing pieces
  - Tactically interesting (harder to block)
  - Complements diagonal/horizontal pieces
  - Classic, proven pattern from chess
- **Score**: 3-4 (medium value, tactical piece)
- **Example**: Can move from (0,0) to (2,1), (1,2), (-2,1), etc.

### 2. **Scout / Recon** (Pure Diagonal Movement)
**Movement Pattern**: Diagonal-only, longer range
- **Move**: 2-3 tiles diagonally only
- **Attack**: Same as movement (2-3 tiles diagonal)
- **Special**: Fast diagonal flanker
- **Why It Works**:
  - Pure diagonal is underutilized (only Missile uses it, but single-use)
  - Creates different tactical angles
  - Complements horizontal/vertical pieces
  - Fast but predictable
- **Score**: 2-3 (light, fast piece)
- **Example**: Can move from (0,0) to (2,2), (3,3), (-2,-2), etc.

### 3. **Sniper** (Long-Range Diagonal Attack, Limited Movement)
**Movement Pattern**: Minimal movement, long-range diagonal attack
- **Move**: 1 tile horizontally or vertically only (slow, careful positioning)
- **Attack**: 3-6 tiles diagonally (long-range precision)
- **Special**: Cannot attack adjacent tiles (startI=2 like Artillery/Missile)
- **Why It Works**:
  - Different movement vs attack pattern (like Infantry)
  - Long-range threat creates different tactical zones
  - Slow movement balances powerful attack
  - Diagonal complements Artillery's horizontal/vertical
- **Score**: 4-5 (valuable, but vulnerable)
- **Example**: Moves 1 tile, but can attack diagonally across board

### 4. **Helicopter / Gunship** (L-Shape + Jump)
**Movement Pattern**: L-shapes OR straight lines with jump ability
- **Move**: Either L-shape (2+1) OR 1-2 tiles horizontal/vertical
- **Attack**: Same as movement
- **Special**: Can jump over pieces (like Cavalry) when using L-shape
- **Why It Works**:
  - Flexible movement (L-shape OR straight)
  - Jump ability adds tactical depth
  - More mobile than Tank, less predictable than Bomber
  - Combines two movement types
- **Score**: 4-5 (versatile, valuable)
- **Example**: Can L-jump over obstacles OR move straight like Tank

### 5. **Mortar** (Area Attack Pattern)
**Movement Pattern**: Limited movement, area-effect attack
- **Move**: 1 tile in any direction (slow)
- **Attack**: 2-4 tiles horizontal/vertical, but hits target + adjacent tiles (3-tile line)
- **Special**: Area effect - damages target and tiles on either side
- **Why It Works**:
  - Different attack pattern (area vs single target)
  - Creates new tactical considerations
  - Complements single-target pieces
  - Forces enemy spacing
- **Score**: 4-5 (powerful but slow)
- **Example**: Attacks (3,3) and also hits (2,3) and (4,3)

### 6. **Paratrooper / Air Drop** (Teleport-Style Movement)
**Movement Pattern**: Limited teleportation
- **Move**: Can "drop" 2-4 tiles away in any direction (teleport, ignores obstacles)
- **Attack**: 1 tile in any direction after landing
- **Special**: Can only move via drop (no normal movement), must attack same turn or wait
- **Why It Works**:
  - Completely unique movement type
  - High mobility but predictable landing zones
  - Creates surprise factor
  - Different from all existing pieces
- **Score**: 3-4 (tactical surprise piece)
- **Example**: Drops from (0,0) to (3,2), then can attack adjacent

### 7. **Engineer / Sapper** (Zigzag Pattern)
**Movement Pattern**: Alternating direction pattern
- **Move**: 1-2 tiles, but must alternate horizontal/vertical (or diagonal/diagonal-perpendicular)
- **Attack**: Same as movement
- **Special**: Can build/repair fortifications (elevation changes)
- **Why It Works**:
  - Unique movement constraint creates interesting paths
  - Complements other pieces
  - Utility role (like Excavator)
- **Score**: 2-3 (utility piece)
- **Example**: Moves 2 right, then must move 1-2 up/down next turn

### 8. **Rocket Launcher** (Variable Range Diagonal)
**Movement Pattern**: Short move, variable diagonal attack
- **Move**: 1 tile in any direction
- **Attack**: 2-4 tiles diagonally, but range varies by elevation (higher = longer range)
- **Special**: Single-use like Missile, but shorter range
- **Why It Works**:
  - Diagonal attack (underutilized)
  - Single-use creates risk/reward
  - Elevation interaction adds depth
- **Score**: 3-4 (tactical single-use)
- **Example**: Like Missile but shorter range, still single-use

---

## Recommended Changes to Existing Pieces

### Option A: Change Bomber to L-Shape
- **Bomber**: Change from 1-4 horizontal/vertical to L-shape movement (2+1 pattern)
- **Why**: Removes horizontal/vertical redundancy with Tank
- **Keeps**: Flying over own pieces, Air Defense vulnerability
- **Result**: More interesting, harder to predict

### Option B: Change Tank to Diagonal Movement
- **Tank**: Change from 1-2 horizontal/vertical to 1-2 diagonal
- **Why**: Diagonal is underutilized, creates different tactical angles
- **Keeps**: Same range, same attack pattern
- **Result**: Different movement type, still feels "tank-like" (can flank)

### Option C: Split Bomber into Two Pieces
- **Bomber**: Keep as is (horizontal/vertical)
- **New "Fighter"**: L-shape movement, can attack after moving
- **Why**: Adds piece diversity without removing existing piece
- **Result**: More pieces, more options

---

## Top 3 Recommendations (Most Impact)

### 1. **Add Cavalry (L-Shape)** ⭐⭐⭐
- **Why**: Completely different movement, proven pattern, high tactical value
- **Impact**: HIGH - Adds unique movement type
- **Complexity**: LOW - Well-understood pattern
- **Fun Factor**: HIGH - Jumping over pieces is satisfying

### 2. **Add Scout (Pure Diagonal)** ⭐⭐⭐
- **Why**: Diagonal is underutilized, fast flanker role
- **Impact**: MEDIUM-HIGH - Adds diagonal movement diversity
- **Complexity**: LOW - Simple diagonal movement
- **Fun Factor**: MEDIUM-HIGH - Fast, mobile piece

### 3. **Change Bomber to L-Shape** ⭐⭐
- **Why**: Removes redundancy with Tank, more interesting movement
- **Impact**: MEDIUM - Changes existing piece
- **Complexity**: LOW - Same piece, different movement
- **Fun Factor**: HIGH - More tactical, less predictable

---

## Movement Pattern Summary

### Current Patterns:
- Horizontal/Vertical: Tank, Bomber, Artillery (attack)
- Diagonal: Missile (attack, single-use)
- Any Direction (1 tile): Commander, Artillery (move), Missile (move), Air Defense
- Mixed: Infantry (move H/V, attack diagonal)

### Proposed New Patterns:
- **L-Shape**: Cavalry, Helicopter (option)
- **Pure Diagonal**: Scout, Sniper (attack)
- **Area Attack**: Mortar
- **Teleport**: Paratrooper
- **Variable Diagonal**: Rocket Launcher

### Complementary Relationships:
- **L-Shape + Diagonal**: Cavalry + Scout = different angles
- **L-Shape + Horizontal**: Cavalry + Tank = different approaches
- **Long Diagonal + Short Diagonal**: Sniper + Scout = diagonal depth
- **Area + Single**: Mortar + Artillery = different attack types

---

## Implementation Considerations

### Easy to Implement (Use Existing Generators):
- **Cavalry**: New L-shape generator (2+1 pattern)
- **Scout**: DiagonalMoveGenerator with maxDistance=3
- **Sniper**: DiagonalMoveGenerator for attack, HorizontalVerticalMoveGenerator for move

### Medium Complexity:
- **Helicopter**: Two movement options (L-shape OR straight)
- **Mortar**: Area effect attack (hits multiple tiles)
- **Paratrooper**: Teleport-style movement (ignore obstacles)

### Special Mechanics Needed:
- **Jump Over Pieces**: For Cavalry, Helicopter
- **Area Effect**: For Mortar
- **Teleport**: For Paratrooper

---

---

## UNIQUE MOVEMENT IDEAS (Non-Chess Patterns)

### 9. **Spider / Crawler** (3-2-1 Pattern)
**Movement Pattern**: Decreasing range pattern
- **Move**: First move 3 tiles, next move 2 tiles, then 1 tile (then resets)
- **Attack**: Same as movement
- **Special**: Movement range cycles: 3 → 2 → 1 → 3 → 2 → 1...
- **Why It's Unique**:
  - Creates rhythm and planning (save the 3-tile move for when you need it)
  - Different from all existing pieces
  - Forces players to think ahead
  - Creates interesting tactical timing
- **Score**: 3-4 (versatile but requires planning)
- **Example**: Turn 1: move 3 tiles, Turn 2: move 2 tiles, Turn 3: move 1 tile, Turn 4: back to 3 tiles

### 10. **Mine Layer / Trapper** (Leaves Trail)
**Movement Pattern**: Normal movement, but leaves "mines" behind
- **Move**: 1-2 tiles in any direction
- **Attack**: Same as movement
- **Special**: Can place "mine" on tile it just left (optional, costs turn)
- **Mine Effect**: Enemy piece that steps on mine is destroyed (or takes damage)
- **Why It's Unique**:
  - Area denial mechanic
  - Creates board control
  - Different from direct combat pieces
  - Forces enemy pathing decisions
- **Score**: 2-3 (utility/control piece)
- **Example**: Moves from (0,0) to (1,0), can leave mine at (0,0)

### 11. **Charging Bull / Ram** (Must Move Straight, Gains Power)
**Movement Pattern**: Must move in straight line, gains attack power
- **Move**: 2-4 tiles in ONE direction (horizontal OR vertical, chosen at start)
- **Attack**: Power increases with distance moved (2 tiles = normal, 4 tiles = double damage)
- **Special**: Cannot change direction mid-move, must move full distance if possible
- **Why It's Unique**:
  - Committed movement (can't stop early)
  - Risk/reward (longer = more powerful but more predictable)
  - Creates different tactical considerations
  - Forces planning and setup
- **Score**: 3-4 (powerful but predictable)
- **Example**: Must move 3 tiles straight, can't stop at 2

### 12. **Teleporter / Phase** (Swap Positions)
**Movement Pattern**: Swaps position with another piece
- **Move**: Can swap position with any friendly piece within 3 tiles
- **Attack**: 1 tile in any direction after teleporting
- **Special**: Can also swap with enemy piece (but takes damage or has restrictions)
- **Why It's Unique**:
  - Position manipulation
  - Can rescue pieces or reposition
  - Completely different movement type
  - Creates surprise factor
- **Score**: 3-4 (tactical repositioning)
- **Example**: Swaps with friendly piece at (3,3), both pieces trade positions

### 13. **Spiral / Vortex** (Spiral Pattern)
**Movement Pattern**: Moves in spiral pattern around a point
- **Move**: Moves in expanding spiral (1 tile, then 2, then 3, etc. in spiral pattern)
- **Attack**: Same as movement
- **Special**: Must follow spiral pattern (can't break pattern)
- **Why It's Unique**:
  - Completely unique movement pattern
  - Creates interesting board coverage
  - Hard to predict
  - Visually interesting
- **Score**: 3-4 (unpredictable but pattern-based)
- **Example**: Moves in spiral: (0,0) → (1,0) → (1,1) → (0,1) → (-1,1) → (-1,0) → (-1,-1) → (0,-1) → (1,-1) → (2,-1)...

### 14. **Mirror / Reflection** (Mirrors Opponent Movement)
**Movement Pattern**: Copies last enemy move in opposite direction
- **Move**: After enemy moves, can move same distance in opposite direction
- **Attack**: 1 tile in any direction
- **Special**: Must wait for enemy to move first, then can "mirror" their move
- **Why It's Unique**:
  - Reactive movement
  - Creates mind games
  - Completely different from proactive pieces
  - Forces opponent to consider their moves
- **Score**: 3-4 (reactive counter-piece)
- **Example**: Enemy moves 2 tiles right, you can move 2 tiles left (or any opposite direction)

### 15. **Chain / Link** (Moves Relative to Other Pieces)
**Movement Pattern**: Movement based on distance from Commander or other pieces
- **Move**: Can move 1-3 tiles, but range = distance from Commander
- **Attack**: Same as movement
- **Special**: Closer to Commander = shorter range, farther = longer range
- **Why It's Unique**:
  - Dynamic range based on position
  - Creates interesting positioning decisions
  - Different from fixed-range pieces
  - Encourages formation play
- **Score**: 3-4 (formation-dependent)
- **Example**: 5 tiles from Commander = can move 5 tiles (capped at 3)

### 16. **Boomerang / Return** (Goes Out and Comes Back)
**Movement Pattern**: Moves to target, then returns to original position
- **Move**: Can "attack" piece 2-4 tiles away, then returns to starting position
- **Attack**: Hits target, then piece returns to original tile
- **Special**: Piece doesn't actually move (stays in place), but can attack at range
- **Why It's Unique**:
  - Ranged attack without moving
  - Safe positioning (doesn't expose piece)
  - Different from all other pieces
  - Creates interesting attack patterns
- **Score**: 3-4 (safe ranged attacker)
- **Example**: At (0,0), can attack (3,2), piece stays at (0,0)

### 17. **Elevation Surfer** (Movement Based on Elevation)
**Movement Pattern**: Movement range = current elevation
- **Move**: Can move N tiles where N = current tile elevation
- **Attack**: Same as movement
- **Special**: Higher elevation = more movement, flat ground = less movement
- **Why It's Unique**:
  - Terrain-dependent movement
  - Encourages elevation control
  - Different from pieces that ignore elevation
  - Creates terrain strategy
- **Score**: 3-4 (terrain-dependent)
- **Example**: On elevation 3 = can move 3 tiles, on elevation 0 = can't move

### 18. **Pincer / Claw** (Two-Step Attack)
**Movement Pattern**: Must move twice to attack
- **Move**: Can move 1 tile, then must move 1 tile in perpendicular direction
- **Attack**: Only attacks on second move
- **Special**: Two-step movement required, can't attack on first move
- **Why It's Unique**:
  - Two-phase movement
  - Creates interesting attack angles
  - Different from single-move pieces
  - Requires setup
- **Score**: 3-4 (setup required but powerful)
- **Example**: Move 1 right, then 1 up, attacks at end of second move

### 19. **Momentum / Slider** (Gains Speed)
**Movement Pattern**: Movement distance increases each turn
- **Move**: Turn 1 = 1 tile, Turn 2 = 2 tiles, Turn 3 = 3 tiles (resets if doesn't move)
- **Attack**: Same as movement
- **Special**: Must move every turn to build momentum, stopping resets to 1
- **Why It's Unique**:
  - Momentum mechanic
  - Rewards continuous movement
  - Creates interesting timing decisions
  - Different from fixed-range pieces
- **Score**: 3-4 (momentum-based)
- **Example**: Turn 1: 1 tile, Turn 2: 2 tiles, Turn 3: 3 tiles, if stops: back to 1

### 20. **Shadow / Stalker** (Follows Enemy)
**Movement Pattern**: Moves toward nearest enemy piece
- **Move**: Must move 1-2 tiles toward nearest enemy (shortest path)
- **Attack**: 1 tile in any direction
- **Special**: Movement is "pulled" toward enemies, can't move away
- **Why It's Unique**:
  - Enemy-driven movement
  - Creates interesting positioning
  - Different from player-controlled movement
  - Forces enemy positioning decisions
- **Score**: 3-4 (aggressive positioning)
- **Example**: Nearest enemy at (5,3), must move toward them (can't move away)

### 21. **Wave / Ripple** (Area Movement)
**Movement Pattern**: Moves in expanding wave pattern
- **Move**: Can move to any tile at exact distance 2, 3, or 4 (ring pattern)
- **Attack**: Same as movement
- **Special**: Can only move to tiles at specific distances (ring pattern)
- **Why It's Unique**:
  - Ring-based movement
  - Creates interesting coverage patterns
  - Different from straight/diagonal movement
  - Hard to predict
- **Score**: 3-4 (ring-based movement)
- **Example**: At (5,5), can move to any tile exactly 3 tiles away (ring of radius 3)

### 22. **Tunneler / Burrow** (Underground Movement)
**Movement Pattern**: Can move through pieces but slower
- **Move**: 1-2 tiles, but can move through pieces (friendly and enemy)
- **Attack**: 1 tile in any direction (must surface to attack)
- **Special**: Moving through pieces costs extra movement, can't attack while "underground"
- **Why It's Unique**:
  - Can bypass obstacles
  - Different from pieces blocked by others
  - Creates interesting pathing
  - Tactical positioning tool
- **Score**: 3-4 (obstacle-bypassing)
- **Example**: Can move through friendly piece, but uses 2 movement instead of 1

---

## Top Unique Ideas (No Chess Patterns)

### Tier 1: Most Interesting & Fun ⭐⭐⭐
1. **Spider (3-2-1 Pattern)** - Rhythm-based movement, requires planning
2. **Charging Bull** - Committed straight-line movement with power scaling
3. **Teleporter (Swap)** - Position manipulation, tactical repositioning
4. **Boomerang** - Ranged attack without moving, safe positioning

### Tier 2: Very Unique ⭐⭐
5. **Mine Layer** - Area denial, board control
6. **Mirror** - Reactive movement, mind games
7. **Momentum Slider** - Speed building mechanic
8. **Elevation Surfer** - Terrain-dependent movement

### Tier 3: Interesting but Complex ⭐
9. **Spiral** - Pattern-based but complex
10. **Chain** - Formation-dependent
11. **Shadow** - Enemy-driven movement
12. **Wave/Ripple** - Ring-based movement

---

## Final Thoughts

**Best Unique Additions (No Chess):**
1. **Spider (3-2-1)** - Rhythm mechanic, requires planning, very unique
2. **Charging Bull** - Committed movement with power scaling, interesting risk/reward
3. **Teleporter (Swap)** - Position manipulation, completely different mechanic
4. **Boomerang** - Safe ranged attack, unique attack pattern

**Why These Work:**
- Completely unique movement patterns (not chess-derived)
- Interesting and fun mechanics
- Reasonable complexity
- Create new tactical situations
- Add genuine diversity to movement types

**Balance Considerations:**
- Each unique mechanic should have clear counter-play
- Some require more planning (Spider, Charging Bull)
- Some are more reactive (Mirror, Shadow)
- Mix creates interesting tactical variety

---

## EVEN MORE UNIQUE IDEAS (Iteration 2)

### 23. **Splitter / Duplicate** (Creates Temporary Copy)
**Movement Pattern**: Can split into two pieces temporarily
- **Move**: Can create "ghost" copy 1-2 tiles away, both can move 1 tile
- **Attack**: Either piece can attack, but only one is "real" (other disappears after)
- **Special**: Must choose which is real at end of turn, other disappears
- **Why It's Unique**:
  - Creates temporary pieces
  - Mind games (which one is real?)
  - Different from single-piece movement
  - Tactical deception
- **Score**: 3-4 (deception piece)
- **Example**: Create copy at (2,2), move original to (1,1), move copy to (3,3), choose which is real

### 24. **Anchor / Tether** (Moves Relative to Fixed Point)
**Movement Pattern**: Can "anchor" to a tile, then moves in arc around it
- **Move**: Can anchor to current position, then move in arc (semicircle) around anchor
- **Attack**: Same as movement
- **Special**: Can re-anchor to new position, old anchor releases
- **Why It's Unique**:
  - Arc-based movement
  - Creates interesting coverage patterns
  - Different from straight movement
  - Tactical positioning tool
- **Score**: 3-4 (arc-based movement)
- **Example**: Anchor at (5,5), can move in arc to (7,4), (8,5), (7,6), etc.

### 25. **Ricochet / Bounce** (Bounces Off Edges)
**Movement Pattern**: Moves in straight line, bounces off board edges
- **Move**: Moves 2-4 tiles in one direction, bounces off edges and continues
- **Attack**: Hits first enemy piece it encounters (bounce or not)
- **Special**: Can bounce multiple times, unpredictable final position
- **Why It's Unique**:
  - Edge-bouncing mechanic
  - Unpredictable paths
  - Different from all other pieces
  - Creates interesting board coverage
- **Score**: 3-4 (unpredictable movement)
- **Example**: Move right from (8,5), hits edge, bounces left, continues to (6,5)

### 26. **Phase / Blink** (Teleports Through Pieces)
**Movement Pattern**: Can phase through pieces but not stop on them
- **Move**: 2-3 tiles in any direction, can pass through pieces but can't end on occupied tile
- **Attack**: 1 tile in any direction (must be adjacent after phasing)
- **Special**: Can phase through both friendly and enemy pieces
- **Why It's Unique**:
  - Obstacle bypassing
  - Different from pieces blocked by others
  - Creates interesting pathing
  - Tactical positioning
- **Score**: 3-4 (obstacle-bypassing)
- **Example**: Can phase through piece at (2,2) to reach (3,3), but can't stop at (2,2)

### 27. **Vortex / Pull** (Pulls Pieces Toward It)
**Movement Pattern**: Normal movement, but pulls nearby pieces
- **Move**: 1-2 tiles in any direction
- **Attack**: Same as movement
- **Special**: After moving, all pieces within 1 tile are pulled 1 tile toward it
- **Why It's Unique**:
  - Position manipulation
  - Area effect on movement
  - Different from direct combat
  - Creates board control
- **Score**: 3-4 (position control)
- **Example**: Moves to (3,3), pulls piece at (4,3) to (3,3) (if empty) or (3,2)

### 28. **Sticky / Glue** (Sticks to Pieces)
**Movement Pattern**: Can attach to other pieces
- **Move**: Can "attach" to adjacent friendly piece, moves with them
- **Attack**: 1 tile in any direction (when attached or free)
- **Special**: When attached, moves with host piece, can detach anytime
- **Why It's Unique**:
  - Attachment mechanic
  - Moves with other pieces
  - Different from independent movement
  - Tactical positioning
- **Score**: 2-3 (support/attachment piece)
- **Example**: Attach to Tank, moves with Tank, can detach to attack

### 29. **Pulse / Burst** (Expanding/Contracting)
**Movement Pattern**: Movement range pulses (expands and contracts)
- **Move**: Turn 1 = 1 tile, Turn 2 = 3 tiles, Turn 3 = 1 tile, Turn 4 = 3 tiles...
- **Attack**: Same as movement
- **Special**: Alternates between short and long range
- **Why It's Unique**:
  - Pulsing range
  - Creates rhythm
  - Different from fixed-range pieces
  - Requires timing
- **Score**: 3-4 (rhythm-based)
- **Example**: Odd turns = 1 tile, even turns = 3 tiles

### 30. **Chain Reaction / Domino** (Triggers on Movement)
**Movement Pattern**: Normal movement, but triggers chain effects
- **Move**: 1-2 tiles in any direction
- **Attack**: Same as movement
- **Special**: When moving, pushes adjacent pieces 1 tile in same direction
- **Why It's Unique**:
  - Chain effects
  - Position manipulation
  - Different from direct movement
  - Creates interesting board states
- **Score**: 3-4 (chain effect piece)
- **Example**: Moves right, pushes piece to right 1 tile, that piece pushes next piece, etc.

### 31. **Time Delay / Echo** (Moves After Delay)
**Movement Pattern**: Movement happens next turn
- **Move**: Can "queue" move 1-2 tiles, executes next turn
- **Attack**: Same as movement (queued)
- **Special**: Can queue multiple moves, executes in order
- **Why It's Unique**:
  - Delayed execution
  - Planning ahead
  - Different from immediate movement
  - Creates interesting timing
- **Score**: 3-4 (delayed movement)
- **Example**: Queue move to (3,3), executes next turn, can queue another move

### 32. **Gravity / Attraction** (Pulled Toward Commander)
**Movement Pattern**: Movement influenced by Commander position
- **Move**: Can move 1-2 tiles, but movement is "pulled" toward Commander
- **Attack**: Same as movement
- **Special**: Movement range increases when closer to Commander
- **Why It's Unique**:
  - Commander-dependent
  - Formation-based
  - Different from independent movement
  - Encourages formation play
- **Score**: 3-4 (formation piece)
- **Example**: Near Commander = 3 tiles, far = 1 tile

### 33. **Fractal / Branch** (Splits Movement Path)
**Movement Pattern**: Movement splits into multiple paths
- **Move**: Moves 2 tiles, then can "branch" to 2 adjacent tiles (creates 2 possible positions)
- **Attack**: Can attack from either branch position
- **Special**: Must choose which branch is real at end of turn
- **Why It's Unique**:
  - Branching paths
  - Multiple possible positions
  - Different from single-path movement
  - Creates uncertainty
- **Score**: 3-4 (branching movement)
- **Example**: Move 2 right, branch to (3,2) or (3,4), choose which is real

### 34. **Magnetic / Repel** (Pushes Pieces Away)
**Movement Pattern**: Normal movement, but pushes pieces away
- **Move**: 1-2 tiles in any direction
- **Attack**: Same as movement
- **Special**: Adjacent enemy pieces are pushed 1 tile away (if possible)
- **Why It's Unique**:
  - Repulsion mechanic
  - Area denial
  - Different from direct combat
  - Creates space
- **Score**: 3-4 (space control)
- **Example**: Moves to (3,3), pushes enemy at (4,3) to (5,3)

---

## Refined Top Picks (Most Fun & Unique)

### Tier S: Most Interesting ⭐⭐⭐
1. **Spider (3-2-1 Pattern)** - Rhythm mechanic, requires planning
2. **Charging Bull** - Committed movement with power scaling
3. **Boomerang** - Safe ranged attack without moving
4. **Splitter** - Creates temporary copies, mind games
5. **Ricochet** - Bounces off edges, unpredictable paths

### Tier A: Very Unique ⭐⭐
6. **Teleporter (Swap)** - Position manipulation
7. **Mine Layer** - Area denial, board control
8. **Momentum Slider** - Speed building mechanic
9. **Phase/Blink** - Phases through pieces
10. **Pulse** - Expanding/contracting range

### Tier B: Interesting ⭐
11. **Mirror** - Reactive movement
12. **Vortex/Pull** - Pulls pieces toward it
13. **Chain Reaction** - Triggers chain effects
14. **Magnetic/Repel** - Pushes pieces away
15. **Anchor/Tether** - Arc-based movement

---

## Design Principles for Unique Movements

1. **Rhythm/Pattern**: Pieces with cycling patterns (Spider, Pulse, Momentum)
2. **Position Manipulation**: Pieces that move others (Teleporter, Vortex, Magnetic)
3. **Risk/Reward**: Pieces with trade-offs (Charging Bull, Boomerang)
4. **Terrain Interaction**: Pieces that use elevation/terrain (Elevation Surfer)
5. **Reactive**: Pieces that respond to board state (Mirror, Shadow)
6. **Area Effects**: Pieces that affect multiple tiles (Mine Layer, Chain Reaction)
7. **Unpredictable**: Pieces with random or complex paths (Ricochet, Spiral)
8. **Deception**: Pieces that create uncertainty (Splitter, Fractal/Branch)

