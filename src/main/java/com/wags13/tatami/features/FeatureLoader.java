package com.wags13.tatami.features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.wags13.tatami.base.Feature;
import com.wags13.tatami.features.shojipanels.ShojiPanels;
import com.wags13.tatami.features.stonelantern.StoneLantern;
import com.wags13.tatami.features.stonepillar.StonePillar;
import com.wags13.tatami.features.tatamimats.TatamiMats;

public class FeatureLoader {

	public static List<Class<? extends Feature>> featureClasses = new ArrayList<>();
	public static Map<Class<? extends Feature>, Feature> featureClassInstanceMap = new HashMap<>();
	
	public static void setup() {
		registerFeature(TatamiMats.class);
		registerFeature(ShojiPanels.class);
		registerFeature(StonePillar.class);
		registerFeature(StoneLantern.class);
	}
	
	public static void preInit() {
		setup();
		featureClasses.forEach(clazz -> {
			try {
				Feature instance = clazz.newInstance();
				featureClassInstanceMap.put(clazz,  instance);
			} catch (Exception e) {
				throw new RuntimeException("Can't initialize module " + clazz, e);
			}
		});
		
		forEachFeature(feature -> feature.preInit());
	}

	private static void forEachFeature(Consumer<Feature> lambda) {
		featureClassInstanceMap.values().forEach(lambda);
	}

	private static void registerFeature(Class<? extends Feature> clazz) {
		if(!featureClasses.contains(clazz)) featureClasses.add(clazz);
	}
}
