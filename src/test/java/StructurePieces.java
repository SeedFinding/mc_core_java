import com.seedfinding.mccore.gen.PieceInfo;
import com.seedfinding.mccore.nbt.NBTIO;
import com.seedfinding.mccore.nbt.tag.NBTCompound;
import com.seedfinding.mccore.version.MCVersion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class StructurePieces {

    private static final String PATH = "src/test/java/structures/";

    public static void main(String[] args) throws IOException {
    	if (true) return;
        File plainsHouses = new File(PATH + "");

        List<Path> files = Files.walk(plainsHouses.toPath()).collect(Collectors.toList());

        files.forEach(path -> {
            File file = new File(path.toUri());
            if(!file.getName().endsWith(".nbt"))return;

            try {
                NBTCompound nbt = NBTIO.read(file);
                System.out.println("========================================" + file.getName());
                PieceInfo structure = new PieceInfo(MCVersion.v1_16_2).fromTag(nbt);
            } catch(IOException e) {
                e.printStackTrace();
            }
        });


    }

}
