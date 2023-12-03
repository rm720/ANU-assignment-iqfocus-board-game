Framework

Enum: DefaultTile which has tileType and arrayList of associated Cells (see Cell class).
  Encode colour and orientation with int variable for cheaper computation;
Classes:
  1.Cell
  (properties: coordinates(XY) and colour.)
  (methods: rotation)
  2. Tile
  (properties: array of Cells, orientation)
  (methods: rotation (uses cell rotation method)
  (methods: placementToTile, rotateTile, doesTilesOverlap)
  3.BoardState
  (properties: double array of Cells or Colours (decide in future))
  (method: update the board)
  (Public methods: isPlacementLegal, findCandidatePlacements, findWinPlacement)