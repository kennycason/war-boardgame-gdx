# War Board Game - AI Summary

## Overview
War is a deterministic, chess-like strategy board game built with LibGDX. The game features diverse pieces with unique movement and attack patterns, terrain elevation mechanics, and tactical depth without randomness in movement.

## Game Board
- **Dimensions**: 11x11 tiles (configurable via `Constants.BOARD_DIMENSIONS`)
- **Tile System**: Each tile has:
  - `elevation`: Integer (0-4 typically, configurable)
  - `piece`: Optional piece reference
  - `highlight`: Visual state for UI
- **Terrain**: Elevation-based terrain affects movement and provides tactical advantages
- **Default Terrain**: Uses `DefaultTerrainV2Generator` which creates varied elevation patterns

## Game Flow
- **Turn-based**: Players alternate turns (BLACK and WHITE)
- **Turn Structure**: Each player moves 1 piece per turn
- **Scoring**: Points are awarded for destroying enemy pieces based on piece value
- **Win Conditions**:
  - Win if opponent's Commander dies
  - Win if opponent's only remaining piece is their Commander
  - Lose if you have no attack pieces remaining (excluding Commander)
  - Draw if only Commanders remain
  - Draw if same two pieces repeat moves more than twice (endless cycle)

## Core Movement Rules

### Universal Constraints
1. **Elevation Limits**: Pieces cannot ascend/descend more than 1 unit of elevation per tile during movement
2. **Piece Blocking**: Pieces cannot move through or over other pieces (unless specified)
3. **Wall/Gap Restrictions**: Cannot move diagonally around walls or over gaps
   - Wall: Tile 2+ units higher than neighboring tile
   - Gap: Neighboring tile 2+ units lower than current tile
4. **Elevation Check**: Movement stops if elevation difference between adjacent tiles > 1

### Line-of-Sight (Documented but Not Fully Implemented)
- Path can be drawn using diagonal, vertical, or horizontal lines
- Elevation changes must not block line-of-sight unless target is visible to another friendly piece
- Cannot see through walls and pieces (unless specified)
- **Note**: Currently, Artillery and Missile attacks use `ignoreHeight = true` and `canGoThroughPieces = true`, effectively bypassing line-of-sight checks

## Piece Types and Rules

### Commander
- **Score**: 100.0 (infinite value - game ends if destroyed)
- **Movement**: 1 tile in any direction (horizontal, vertical, diagonal)
- **Attack**: Same as movement (can attack 1 tile in any direction)
- **Special**: Starting position in corner-most tile
- **Implementation**: Uses both `HorizontalVerticalMoveGenerator` and `DiagonalMoveGenerator` with maxDistance=1, canAttack=true

### Infantry
- **Score**: 1.0
- **Movement**: 1 tile horizontally or vertically only
- **Attack**: 1 tile diagonally only
- **Special**: Can attack from any elevation (no height restriction on attacks)
- **Implementation**: 
  - Movement: `HorizontalVerticalMoveGenerator(maxDistance=1, canAttack=false)`
  - Attack: `DiagonalMoveGenerator(maxDistance=1, requiredAttack=true)`

### Tank
- **Score**: 2.0 (in code) / 3.0 (in README)
- **Movement**: 1-2 tiles straight horizontally or vertically
- **Attack**: Same as movement (1-2 tiles horizontally/vertically)
- **Implementation**: `HorizontalVerticalMoveGenerator(maxDistance=2, canAttack=true)`

### Artillery
- **Score**: 3.0 (in code) / 5.0 (in README)
- **Movement**: 1 tile in any direction
- **Attack**: 
  - Range: 2-3 tiles horizontally or vertically (base)
  - Range increases by 1 tile per unit of elevation difference
  - Cannot fire into immediately neighboring tiles (startI=2)
  - Can fire over pieces and small walls (`ignoreHeight=true`, `canGoThroughOwnPiece=true`, `canGoThroughEnemyPiece=true`)
- **Reloading**:
  - Cannot fire for 1 turn after attacking
  - Reloads automatically after 3+ turns (`board.turnCount - lastAttackTurn > 3`)
  - **Note**: README mentions immediate auto-reload if next to Commander, but this is NOT implemented in code
- **Implementation**:
  - Movement: `HorizontalVerticalMoveGenerator(maxDistance=1)` + `DiagonalMoveGenerator(maxDistance=1)`, both `canAttack=false`
  - Attack: `HorizontalVerticalMoveGenerator(startI=2, maxDistance=3, requiredAttack=true, ignoreHeight=true, canGoThroughOwnPiece=true, canGoThroughEnemyPiece=true)`

### Missile
- **Score**: 4.0 (in code) / 6.0 (in README)
- **Movement**: 1 tile in any direction
- **Attack**:
  - Range: 2-5 tiles diagonally (base)
  - Range increases by 1 tile per unit of elevation difference
  - Cannot fire into immediately neighboring tiles (startI=2)
  - Can fire over pieces and small walls (`ignoreHeight=true`, `canGoThroughPieces=true`)
  - **Single-use**: Missile is destroyed after attacking (removed from board)
  - **Air Defense Interaction**: If Air Defense intercepts, missile is destroyed but target survives
- **Scoring**: When missile attacks, score is reduced by `(MISSILE.score - attackThreshold)` where `attackThreshold=1`
- **Implementation**:
  - Movement: `HorizontalVerticalMoveGenerator(maxDistance=1)` + `DiagonalMoveGenerator(maxDistance=1)`, both `canAttack=false`
  - Attack: `DiagonalMoveGenerator(maxDistance=5, startI=2, canGoThroughPieces=true, requiredAttack=true, ignoreHeight=true)`

### Air Defense
- **Score**: 4.0
- **Movement**: 1 tile in any direction
- **Attack**: Cannot attack directly (defense only)
- **Defense**:
  - Protects against Artillery shells, Missiles, and Bombers
  - Range: 1 tile in any direction (8 surrounding tiles)
  - **Single-use**: Gets destroyed after defending
  - **Auto-fire**: Automatically intercepts if enemy piece enters fire-zone
  - **Multiple Air Defense**: If 2+ Air Defense systems in range, defending player chooses (not fully implemented - first found is used)
  - **Retreat Protection**: Can intercept pieces retreating from Air Defense
- **Implementation**:
  - Movement: `HorizontalVerticalMoveGenerator(maxDistance=1)` + `DiagonalMoveGenerator(maxDistance=1)`, both `canAttack=false`
  - Detection: `AirDefenseDetector` checks 8 surrounding tiles for enemy Artillery/Missile/Bomber attacks

### Bomber
- **Score**: 5.0 (in code) / 7.0 (in README)
- **Movement**: 1-4 tiles horizontally or vertically
- **Attack**: Same as movement (1-4 tiles horizontally/vertically)
- **Special**:
  - Elevation does NOT affect Bomber (`ignoreHeight=true`)
  - Can fly over own team's pieces (`canGoThroughOwnPiece=true`)
  - Cannot fly over opponent's pieces
  - Vulnerable to Air Defense (gets destroyed if Air Defense intercepts)
- **Implementation**: `HorizontalVerticalMoveGenerator(maxDistance=4, canAttack=true, ignoreHeight=true, canGoThroughOwnPiece=true)`

### Excavator (Optional)
- **Score**: 3.0
- **Movement**: 1 tile in any direction
- **Attack**: Cannot attack pieces
- **Special Ability**: Can raise or lower neighboring tiles by 1 unit of elevation
  - Minimum elevation: 0
  - Maximum elevation: 4
  - Can create/remove walls and gaps
  - Excavation moves have `MoveType.ATTACK` with `elevationDelta` of +1 or -1
  - Excavation moves have minimal score (0.01)
- **Implementation**:
  - Movement: `HorizontalVerticalMoveGenerator(maxDistance=1)` + `DiagonalMoveGenerator(maxDistance=1)`, both `canAttack=false`
  - Excavation: Generates additional moves for each neighboring tile to raise/lower elevation

### Transport (Optional - Not Implemented)
- **Score**: 2.0
- **Movement**: 1-3 tiles horizontally or vertically
- **Special**: Can load/unload pieces
  - 1 turn to load piece from neighboring tile
  - 1 turn to drop off piece to neighboring tile
  - Pieces inactive while transported
  - If transport destroyed, both transport and cargo destroyed

## Starting Formation
- **Commander**: Starts in corner-most tile
- **Formation Order** (around Commander):
  1. Air Defense
  2. Missile
  3. Bomber
  4. Artillery
  5. Tank
  6. Infantry
  7. Transport (optional)
  8. Excavator (optional)
- **Common Formations**: 4x4 grid (16 pieces), 3x6 grid (18 pieces), 5x5 grid (25 pieces)
- **Current Implementation**: `PrimaryFormationPiecePlacer` places pieces in a specific pattern for both players

## Scoring System

### Piece Values (Current Implementation)
| Piece | Score (Code) | Score (README) |
|-------|--------------|----------------|
| Commander | 100.0 | ∞ |
| Bomber | 5.0 | 7 |
| Missile | 4.0 | 6 |
| Air Defense | 4.0 | 4 |
| Artillery | 3.0 | 5 |
| Tank | 2.0 | 3 |
| Excavator | 3.0 | 2 |
| Infantry | 1.0 | 1 |

### Scoring Rules
- Points awarded when enemy piece is destroyed
- **Missile Exception**: No points if missile is destroyed by Air Defense after use
- **Air Defense Exception**: No points if Air Defense is sacrificed to defend (must be directly attacked)
- **Win Condition**: Game ends when a player's score >= Commander.score (100.0)

## AI System

### Move Evaluation
- **MoveScorer** evaluates moves based on:
  - Base score (piece value destroyed)
  - Commander advance (bonus for moving closer to enemy Commander)
  - Weaker piece movement (bonus for moving less valuable pieces)
  - Random noise (for move diversity)
- **Player Modifier**: Flips score sign based on current player

### AI Implementation
- **MiniMaxCarlo**: Monte Carlo tree search with MiniMax
- **Configuration**: 
  - Max depth: 3
  - Noise: 0.2
  - Random walk probability: 0.05
  - Max random walk depth: 4

## Implementation Details

### Move Generation
- **HorizontalVerticalMoveGenerator**: Handles horizontal/vertical movement
- **DiagonalMoveGenerator**: Handles diagonal movement
- **InfantryMoveGenerator**: Special generator for Infantry (separate move/attack patterns)
- **Parameters**:
  - `maxDistance`: Maximum tiles piece can move
  - `startI`: Starting distance (for attacks that can't hit adjacent tiles)
  - `canAttack`: Whether piece can attack
  - `requiredAttack`: Whether move must be an attack
  - `canGoThroughOwnPiece`: Can move through friendly pieces
  - `canGoThroughEnemyPiece`: Can move through enemy pieces
  - `ignoreHeight`: Ignores elevation restrictions

### Move Application
- **applyMove()**: Applies move to board, updates piece positions, calculates scores
- **undoMove()**: Reverses move (for AI search)
- **Turn Management**: Automatically switches players after move

### Air Defense Detection
- **AirDefenseDetector**: Checks 8 surrounding tiles for Air Defense
- **updateScoreBaseOnAirDefense()**: Adjusts move score if Air Defense would intercept
- **getNeighborAirDefense()**: Returns Air Defense piece if in range

## Known Discrepancies

### Between README and Implementation
1. **Artillery Reload**: README says "Immediate auto-reloads in same round if positioned next to Commander" - NOT implemented
2. **Artillery Range**: README says "1 to 3 tiles plus 1 tile per elevation" - Implementation uses 2-3 tiles base (startI=2)
3. **Piece Scores**: Some discrepancies between README table and `PieceType` enum
4. **Line-of-Sight**: Documented in README but not fully implemented (Artillery/Missile use `ignoreHeight=true`)

### Missing Features
1. **Transport**: Documented but not implemented
2. **Line-of-Sight**: Mentioned in rules but not fully implemented
3. **Water/Mud Tiles**: Mentioned as optional rules but not implemented
4. **Multi-use Air Defense/Missile**: Optional rules not implemented

## Game State Management
- **Board**: Contains tile grid, turn count, current player, scores
- **Piece**: Abstract base class with position, player, type
- **Move**: Contains move information (from/to, type, score, destroyed piece, air defense interaction)
- **Turn Count**: Tracks number of turns for reloading mechanics

## Testing
- Unit tests exist for: Infantry, Tank, Bomber, Air Defense, Excavator, MoveScorer
- Tests verify move generation, application, and undo functionality
- Air Defense tests verify interception mechanics

## Future Considerations
- Implement full line-of-sight checking
- Add Transport piece
- Implement Commander adjacency auto-reload for Artillery
- Add water/mud terrain types
- Implement multi-use Air Defense/Missile optional rules
- Balance piece scores between README and code
- Add more sophisticated win condition checking

