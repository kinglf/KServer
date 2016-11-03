package Database;

import cn.trafficdata.KServer.common.model.Project;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * Created by Kinglf on 2016/11/3.
 */
public class ProjectCodec implements Codec<Project> {
    @Override
    public Project decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Project project, EncoderContext encoderContext) {

    }

    @Override
    public Class<Project> getEncoderClass() {
        return null;
    }
}
