import com.seedfinding.mccore.block.Blocks;
import com.seedfinding.mccore.gen.PieceInfo;
import com.seedfinding.mccore.nbt.NBTIO;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.version.MCVersion;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Test {

    public static void main(String[] args) throws IOException {
        URL url= Test.class.getResource("plains_meeting_point_1.nbt");
        assert url != null;
        File file=new File(url.getFile());
        PieceInfo structure = new PieceInfo(MCVersion.v1_16_2).fromTag(NBTIO.read(file));
        structure.getPalette().replace(Blocks.COBBLESTONE, Blocks.STONE);
        structure.getBlockIDs().put(new BPos(0, 0, 0), structure.getPalette().indexOf(Blocks.STONE));
        File got = File.createTempFile("plains_meeting_point_1_modified", ".nbt");
        String outputPath = got.getAbsolutePath();
        System.out.println("Wrote file to "+outputPath);
        NBTIO.write(structure.toTag(), outputPath, true);
    }

}
