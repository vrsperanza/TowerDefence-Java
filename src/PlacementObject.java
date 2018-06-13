import java.awt.image.BufferedImage;

public class PlacementObject extends GameObject {	
	static PlacementObject placementObject = null;
	static ObjectType objectType = null;
	static GameObject toPlace = null;
	
	public PlacementObject(ObjectType objectType) {
		if(placementObject != null)
			Game.destroy(placementObject);
		
		PlacementObject.objectType = objectType;
		placementObject = this;
		position = Input.mousePosition;
		
		switch(objectType) {
			case BasicTurrent:
				toPlace = new BasicTurrent();
				break;
			case AreaTurrent:
				toPlace = new AreaTurrent();
				break;
			case BarricadeTurrent:
				toPlace = new BarricadeTurrent();
				break;
			case Enemy:
				toPlace = new BasicEnemy();
				break;
			default:
				break;
		}
		image = Image.toGray(toPlace.defaultImage());
	}
	
	public void place() {
		for(GameObject g : Game.gameObjects) {
			if(g != this && Grid.intersect(position, g.position)) {
				return;
			}
		}
		
		toPlace.position = Grid.snap(position);
		Game.instantiate(toPlace);
		
		Game.destroy(this);
		if(Input.shift)
			Game.instantiate(new PlacementObject(objectType));
	}

	@Override
	public void instantiate() {
		
	}

	@Override
	public void loop() {
		position = Grid.snap(Input.mousePosition);
		if(Input.mousePress)
			place();
		if(Input.rightMousePress) {
			Game.destroy(this);
			placementObject = null;
		}
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public BufferedImage defaultImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
