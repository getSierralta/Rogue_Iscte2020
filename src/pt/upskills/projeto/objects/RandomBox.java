package pt.upskills.projeto.objects;

import java.util.ArrayList;

public class RandomBox<T> {

	private ArrayList<ObjectChancePair> objects = new ArrayList<>();

	private class ObjectChancePair {
		T object;
		double weight;

		public ObjectChancePair(T object, double weight) {
			this.object = object;
			this.weight = weight;
		}
	}

	public void addObject(T o, double weight) {
		objects.add(new ObjectChancePair(o, weight));
	}

	public T getRandomObject() {
		if (objects.size() == 0)
			return null;
		double totalWeight = 0;
		for (ObjectChancePair pair : objects)
			totalWeight += pair.weight;

		double rand = Math.random() * totalWeight;
		double weightSum = 0;
		for (ObjectChancePair pair : objects) {
			weightSum += pair.weight;
			if (weightSum > rand)
				return pair.object;
		}
		return null;
	}
}
