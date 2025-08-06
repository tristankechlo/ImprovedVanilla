package com.tristankechlo.improvedvanilla.platform;

import com.tristankechlo.improvedvanilla.ImprovedVanilla;

import java.nio.file.Path;

public interface IPlatformHelper {

    IPlatformHelper INSTANCE = ImprovedVanilla.load(IPlatformHelper.class);

    Path getConfigDirectory();

}
