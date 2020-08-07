# PluginDIExample
Cleaned up version of the orignal repo posted in comments of this Spigot DI plugin example taken from this [article](https://www.spigotmc.org/threads/tutorial-spigot-plugins-dependency-injection.295218/) about DI with MC plugins by [MrDienns](https://www.spigotmc.org/members/mrdienns.35704/) on spigotmc.org

Note you should not use the Master branch as it won't work. I will look to maintain develop to ensure it works with the latest release of spigot but don't hold your breath. And if it does fail look for a branch that matches your server version.

Guava is already shaded into bukkit so as pointed out [here](https://www.spigotmc.org/posts/3907814/) by [xTrollxDudex](https://www.spigotmc.org/members/xtrollxdudex.6621/), so the exception we are getting :
<pre><code>06.08 13:08:38 [Server] INFO java.lang.NoSuchMethodError:
 com.google.common.base.Preconditions.checkArgument(ZLjava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V</code></pre>
appears to be a clash with version bukkit shades and the version our plugin shadows _(gradle shading)_.

This [post](https://www.spigotmc.org/threads/version-of-guava-in-current-spigots.92334/) talks about versions of Guava in Spigot, and we will attempt to use this to work out which version of Guice we should fall back to.

We could consider actually relocating the latest version of Guava to match a version Guice we select but this causes extra work in creating shadow relocation tasks, increases the size of our plugins and still potentially raises compatibility issues that could cause much pain.

The repo we need to inspect is [here](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/browse) however it appears that releases are not tagged and are maintained in short lived feature branches.

Versions prior to 1.8.1-R4 don't use but guava-collections (see the pom change in this commit [489d3e31386](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/diff/pom.xml?until=489d3e3138600ad208685d61fd2abb1885806952))

To work it out I used the following info:
[pom.xml](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/browse/pom.xml?until=643b7d56fb3f6040df9b3f4eb559beb44153f165&untilPath=pom.xml)
1.8.8 => guava 17.0 [( April 22, 2014.)](https://github.com/google/guava/wiki/Release17) => Guice 4.0 ([28 Apr 2015](https://github.com/google/guice/wiki/Guice40))
and updated the build file with [1.8-R0.1-SNAPSHOT](https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/1.8-R0.1-SNAPSHOT/) looking [here](https://hub.spigotmc.org/nexus/content/repositories/snapshots/org/spigotmc/spigot-api/).