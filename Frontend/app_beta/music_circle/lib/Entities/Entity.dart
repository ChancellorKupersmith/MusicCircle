enum EntityType{
  USER,
  GROUP,
  ALBUM,
  AUDIO_FILE,
  EVENT,
  GENRE,
  PLAYLIST,
  COMMENT,
  CHAT,
  POST,
}
abstract class Entity{
  String id, name;
  EntityType getType();
}