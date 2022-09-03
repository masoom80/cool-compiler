// DO NOT EDIT
// Generated by JFlex 1.8.2 http://jflex.de/
// source: scanner.flex

  /* JFlex example: partial Java language lexer specification */


    /*
     * This class is a simple example lexer.
     */



// See https://github.com/jflex-de/jflex/issues/222
@SuppressWarnings("FallThrough")
public class Scanner implements Lexical {

  /** This character denotes the end of file. */
  public static final int YYEOF = -1;

  /** Initial size of the lookahead buffer. */
  private static final int ZZ_BUFFERSIZE = 16384;

  // Lexical states.
  public static final int YYINITIAL = 0;
  public static final int STRING = 2;
  public static final int STRINGBACK = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2, 2
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\u10ff\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\0\1\1\1\3\22\0\1\1"+
    "\1\4\1\5\2\0\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\11\22"+
    "\1\0\1\23\1\24\1\25\1\26\2\0\4\27\1\30"+
    "\1\27\2\31\1\32\11\31\1\33\4\31\1\34\2\31"+
    "\1\35\1\36\1\37\1\40\1\41\1\0\1\42\1\43"+
    "\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\31"+
    "\1\53\1\54\1\31\1\55\1\56\1\57\1\31\1\60"+
    "\1\61\1\62\1\63\1\64\1\65\1\34\2\31\1\66"+
    "\1\67\1\70\u0182\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[512];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\2\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\2\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\15\24\1\30\1\31\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46"+
    "\1\47\1\50\1\51\1\52\1\53\1\0\1\54\1\55"+
    "\1\56\2\0\1\57\1\60\1\61\11\24\2\62\12\24"+
    "\1\63\2\0\2\54\2\56\1\0\2\64\1\65\7\24"+
    "\1\62\12\24\1\0\1\24\1\62\12\24\1\62\11\24"+
    "\1\62\5\24\1\62\27\24";

  private static int [] zzUnpackAction() {
    int [] result = new int[173];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\71\0\162\0\253\0\253\0\344\0\u011d\0\253"+
    "\0\253\0\u0156\0\253\0\253\0\u018f\0\u01c8\0\253\0\u0201"+
    "\0\253\0\u023a\0\u0273\0\u02ac\0\253\0\u02e5\0\u031e\0\u0357"+
    "\0\u0390\0\253\0\253\0\253\0\u03c9\0\u0402\0\u043b\0\u0474"+
    "\0\u04ad\0\u04e6\0\u051f\0\u0558\0\u0591\0\u05ca\0\u0603\0\u063c"+
    "\0\u0675\0\253\0\u06ae\0\253\0\u06e7\0\253\0\253\0\253"+
    "\0\253\0\253\0\253\0\253\0\253\0\253\0\253\0\253"+
    "\0\253\0\253\0\253\0\253\0\253\0\u0720\0\u0759\0\253"+
    "\0\u0792\0\u07cb\0\u0804\0\253\0\253\0\253\0\u083d\0\u0876"+
    "\0\u08af\0\u08e8\0\u0921\0\u095a\0\u0993\0\u09cc\0\u0a05\0\u083d"+
    "\0\u0a3e\0\u0a77\0\u0ab0\0\u0ae9\0\u0b22\0\u0b5b\0\u0b94\0\u0bcd"+
    "\0\u0c06\0\u0c3f\0\u0c78\0\253\0\u0cb1\0\u0cea\0\253\0\u0d23"+
    "\0\u0d5c\0\u0d95\0\u0dce\0\253\0\u0e07\0\u0804\0\u0e40\0\u0e79"+
    "\0\u0eb2\0\u0eeb\0\u0f24\0\u0f5d\0\u0f96\0\u0e40\0\u0fcf\0\u1008"+
    "\0\u1041\0\u107a\0\u10b3\0\u10ec\0\u1125\0\u115e\0\u1197\0\u11d0"+
    "\0\u1209\0\u1242\0\u1242\0\u127b\0\u12b4\0\u12ed\0\u1326\0\u135f"+
    "\0\u1398\0\u13d1\0\u140a\0\u1443\0\u147c\0\u147c\0\u14b5\0\u14ee"+
    "\0\u1527\0\u1560\0\u1599\0\u15d2\0\u160b\0\u1644\0\u167d\0\u15d2"+
    "\0\u16b6\0\u16ef\0\u1728\0\u1761\0\u179a\0\u179a\0\u17d3\0\u180c"+
    "\0\u1845\0\u187e\0\u18b7\0\u18f0\0\u1929\0\u1962\0\u199b\0\u19d4"+
    "\0\u1a0d\0\u1a46\0\u1a7f\0\u1ab8\0\u1af1\0\u1b2a\0\u1b63\0\u1b9c"+
    "\0\u1bd5\0\u1c0e\0\u1c47\0\u1c80\0\253";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[173];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\2\5\1\6\1\7\1\10\1\11\1\12\1\4"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\6\31\1\32"+
    "\1\4\1\33\1\34\1\4\1\31\1\35\1\36\1\31"+
    "\1\37\1\40\2\31\1\41\1\31\1\42\1\43\1\31"+
    "\1\44\1\45\1\46\1\47\1\31\1\50\1\51\1\52"+
    "\1\53\1\54\5\55\1\56\30\55\1\57\32\55\5\60"+
    "\1\61\2\60\1\62\25\60\1\63\16\60\1\64\2\60"+
    "\1\65\1\60\1\66\6\60\73\0\1\5\113\0\1\67"+
    "\52\0\1\70\106\0\1\71\57\0\1\72\10\0\1\73"+
    "\61\0\1\74\6\0\1\75\56\0\1\76\4\0\1\77"+
    "\4\0\1\100\62\0\1\101\10\0\1\102\3\0\1\103"+
    "\11\0\1\102\41\0\1\101\1\0\2\24\5\0\1\102"+
    "\15\0\1\102\47\0\1\104\70\0\1\105\70\0\1\106"+
    "\64\0\2\107\4\0\6\107\4\0\25\107\1\0\1\107"+
    "\22\0\2\107\4\0\6\107\4\0\15\107\1\110\1\107"+
    "\1\111\5\107\1\0\1\107\22\0\2\107\4\0\6\107"+
    "\4\0\13\107\1\112\1\107\1\113\7\107\1\0\1\107"+
    "\22\0\2\107\4\0\6\107\4\0\13\107\1\114\11\107"+
    "\1\0\1\107\22\0\2\107\4\0\6\107\4\0\1\107"+
    "\1\115\13\107\1\116\4\107\1\117\2\107\1\0\1\107"+
    "\22\0\2\107\4\0\6\107\4\0\6\107\1\120\5\107"+
    "\1\121\10\107\1\0\1\107\22\0\2\107\4\0\6\107"+
    "\4\0\5\107\1\122\7\107\1\123\7\107\1\0\1\107"+
    "\22\0\2\107\4\0\6\107\4\0\5\107\1\124\17\107"+
    "\1\0\1\107\22\0\2\107\4\0\6\107\4\0\17\107"+
    "\1\125\5\107\1\0\1\107\22\0\2\107\4\0\6\107"+
    "\4\0\1\107\1\126\3\107\1\127\17\107\1\0\1\107"+
    "\22\0\2\107\4\0\6\107\4\0\21\107\1\130\3\107"+
    "\1\0\1\107\22\0\2\107\4\0\6\107\4\0\17\107"+
    "\1\131\5\107\1\0\1\107\22\0\2\107\4\0\6\107"+
    "\4\0\15\107\1\132\7\107\1\0\1\107\22\0\2\107"+
    "\4\0\6\107\4\0\10\107\1\133\14\107\1\0\1\107"+
    "\70\0\1\134\1\0\5\55\1\0\30\55\1\0\32\55"+
    "\13\135\1\136\55\135\2\77\1\137\1\140\65\77\21\0"+
    "\1\141\1\142\5\0\1\102\15\0\1\102\36\0\1\143"+
    "\1\0\1\143\2\0\1\144\1\145\67\0\2\146\4\0"+
    "\2\146\11\0\6\146\42\0\2\147\4\0\6\147\4\0"+
    "\25\147\1\0\1\147\22\0\2\147\4\0\6\147\4\0"+
    "\15\147\1\150\7\147\1\0\1\147\22\0\2\147\4\0"+
    "\6\147\4\0\5\147\1\151\17\147\1\0\1\147\22\0"+
    "\2\147\4\0\6\147\4\0\1\147\1\152\23\147\1\0"+
    "\1\147\22\0\2\147\4\0\6\147\4\0\14\147\1\153"+
    "\10\147\1\0\1\147\22\0\2\147\4\0\6\147\4\0"+
    "\20\147\1\154\4\147\1\0\1\147\22\0\2\147\4\0"+
    "\6\147\4\0\13\147\1\155\11\147\1\0\1\147\22\0"+
    "\2\147\4\0\6\147\4\0\17\147\1\156\5\147\1\0"+
    "\1\147\22\0\2\147\4\0\6\147\4\0\14\147\1\157"+
    "\10\147\1\0\1\147\22\0\2\147\4\0\6\147\4\0"+
    "\16\147\1\160\2\147\1\156\3\147\1\0\1\147\22\0"+
    "\2\147\4\0\6\147\4\0\14\147\1\156\4\147\1\156"+
    "\3\147\1\0\1\147\22\0\2\147\4\0\6\147\4\0"+
    "\15\147\1\161\7\147\1\0\1\147\22\0\2\147\4\0"+
    "\6\147\4\0\24\147\1\156\1\0\1\147\22\0\2\147"+
    "\4\0\6\147\4\0\11\147\1\162\13\147\1\0\1\147"+
    "\22\0\2\147\4\0\6\147\4\0\14\147\1\163\10\147"+
    "\1\0\1\147\22\0\2\147\4\0\6\147\4\0\1\147"+
    "\1\150\17\147\1\164\3\147\1\0\1\147\22\0\2\147"+
    "\4\0\6\147\4\0\1\147\1\165\15\147\1\166\5\147"+
    "\1\0\1\147\22\0\2\147\4\0\6\147\4\0\22\147"+
    "\1\154\2\147\1\0\1\147\22\0\2\147\4\0\6\147"+
    "\4\0\11\147\1\167\13\147\1\0\1\147\22\0\2\147"+
    "\4\0\6\147\4\0\11\147\1\170\13\147\1\0\1\147"+
    "\1\0\13\135\1\171\55\135\13\0\1\136\4\0\1\137"+
    "\52\0\1\137\116\0\1\102\15\0\1\102\43\0\2\142"+
    "\5\0\1\102\15\0\1\102\43\0\1\144\1\145\67\0"+
    "\2\145\67\0\2\172\4\0\6\172\4\0\25\172\1\0"+
    "\1\172\22\0\2\172\4\0\6\172\4\0\13\172\1\173"+
    "\11\172\1\0\1\172\22\0\2\172\4\0\6\172\4\0"+
    "\1\172\1\174\23\172\1\0\1\172\22\0\2\172\4\0"+
    "\6\172\4\0\20\172\1\175\4\172\1\0\1\172\22\0"+
    "\2\172\4\0\6\172\4\0\21\172\1\176\3\172\1\0"+
    "\1\172\22\0\2\172\4\0\6\172\4\0\5\172\1\173"+
    "\17\172\1\0\1\172\22\0\2\172\4\0\6\172\4\0"+
    "\20\172\1\177\4\172\1\0\1\172\22\0\2\172\4\0"+
    "\6\172\4\0\3\172\1\173\21\172\1\0\1\172\22\0"+
    "\2\172\4\0\6\172\4\0\22\172\1\200\2\172\1\0"+
    "\1\172\22\0\2\172\4\0\6\172\4\0\16\172\1\173"+
    "\6\172\1\0\1\172\22\0\2\172\4\0\6\172\4\0"+
    "\14\172\1\201\10\172\1\0\1\172\22\0\2\172\4\0"+
    "\6\172\4\0\7\172\1\177\15\172\1\0\1\172\22\0"+
    "\2\172\4\0\6\172\4\0\22\172\1\202\2\172\1\0"+
    "\1\172\22\0\2\172\4\0\6\172\4\0\21\172\1\203"+
    "\3\172\1\0\1\172\22\0\2\172\4\0\6\172\4\0"+
    "\11\172\1\204\13\172\1\0\1\172\22\0\2\172\4\0"+
    "\6\172\4\0\4\172\1\173\20\172\1\0\1\172\22\0"+
    "\2\172\4\0\6\172\4\0\13\172\1\177\11\172\1\0"+
    "\1\172\1\0\13\135\1\171\4\135\1\137\50\135\21\0"+
    "\2\205\4\0\6\205\4\0\25\205\1\0\1\205\22\0"+
    "\2\205\4\0\6\205\4\0\12\205\1\206\12\205\1\0"+
    "\1\205\22\0\2\205\4\0\6\205\4\0\20\205\1\206"+
    "\4\205\1\0\1\205\22\0\2\205\4\0\6\205\4\0"+
    "\11\205\1\207\13\205\1\0\1\205\22\0\2\205\4\0"+
    "\6\205\4\0\5\205\1\206\17\205\1\0\1\205\22\0"+
    "\2\205\4\0\6\205\4\0\21\205\1\210\3\205\1\0"+
    "\1\205\22\0\2\205\4\0\6\205\4\0\21\205\1\206"+
    "\3\205\1\0\1\205\22\0\2\205\4\0\6\205\4\0"+
    "\17\205\1\211\5\205\1\0\1\205\22\0\2\205\4\0"+
    "\6\205\4\0\11\205\1\212\13\205\1\0\1\205\22\0"+
    "\2\205\4\0\6\205\4\0\14\205\1\213\10\205\1\0"+
    "\1\205\22\0\2\214\4\0\6\214\4\0\25\214\1\0"+
    "\1\214\22\0\2\214\4\0\6\214\4\0\14\214\1\215"+
    "\10\214\1\0\1\214\22\0\2\214\4\0\3\214\1\216"+
    "\1\217\1\214\4\0\25\214\1\0\1\214\22\0\2\214"+
    "\4\0\6\214\4\0\14\214\1\220\10\214\1\0\1\214"+
    "\22\0\2\214\4\0\6\214\4\0\3\214\1\220\21\214"+
    "\1\0\1\214\22\0\2\214\4\0\6\214\4\0\7\214"+
    "\1\220\15\214\1\0\1\214\22\0\2\221\4\0\6\221"+
    "\4\0\25\221\1\0\1\221\22\0\2\221\4\0\6\221"+
    "\4\0\22\221\1\222\2\221\1\0\1\221\22\0\2\221"+
    "\4\0\6\221\4\0\14\221\1\223\10\221\1\0\1\221"+
    "\22\0\2\221\4\0\6\221\4\0\21\221\1\224\3\221"+
    "\1\0\1\221\22\0\2\225\4\0\6\225\4\0\25\225"+
    "\1\0\1\225\22\0\2\225\4\0\6\225\4\0\5\225"+
    "\1\226\17\225\1\0\1\225\22\0\2\225\4\0\6\225"+
    "\4\0\21\225\1\226\3\225\1\0\1\225\22\0\2\225"+
    "\4\0\6\225\4\0\17\225\1\226\5\225\1\0\1\225"+
    "\22\0\2\227\4\0\6\227\4\0\25\227\1\0\1\227"+
    "\22\0\2\230\4\0\6\230\4\0\25\230\1\0\1\230"+
    "\22\0\2\231\4\0\6\231\4\0\25\231\1\0\1\231"+
    "\22\0\2\232\4\0\6\232\4\0\25\232\1\0\1\232"+
    "\22\0\2\233\4\0\6\233\4\0\25\233\1\0\1\233"+
    "\22\0\2\234\4\0\6\234\4\0\25\234\1\0\1\234"+
    "\22\0\2\235\4\0\6\235\4\0\25\235\1\0\1\235"+
    "\22\0\2\236\4\0\6\236\4\0\25\236\1\0\1\236"+
    "\22\0\2\237\4\0\6\237\4\0\25\237\1\0\1\237"+
    "\22\0\2\240\4\0\6\240\4\0\25\240\1\0\1\240"+
    "\22\0\2\241\4\0\6\241\4\0\25\241\1\0\1\241"+
    "\22\0\2\242\4\0\6\242\4\0\25\242\1\0\1\242"+
    "\22\0\2\243\4\0\6\243\4\0\25\243\1\0\1\243"+
    "\22\0\2\244\4\0\6\244\4\0\25\244\1\0\1\244"+
    "\22\0\2\245\4\0\6\245\4\0\25\245\1\0\1\245"+
    "\22\0\2\246\4\0\6\246\4\0\25\246\1\0\1\246"+
    "\22\0\2\247\4\0\6\247\4\0\25\247\1\0\1\247"+
    "\22\0\2\250\4\0\6\250\4\0\25\250\1\0\1\250"+
    "\22\0\2\251\4\0\6\251\4\0\25\251\1\0\1\251"+
    "\22\0\2\252\4\0\6\252\4\0\25\252\1\0\1\252"+
    "\22\0\2\253\4\0\6\253\4\0\25\253\1\0\1\253"+
    "\22\0\2\254\4\0\6\254\4\0\25\254\1\0\1\254"+
    "\22\0\2\255\4\0\6\255\4\0\25\255\1\0\1\255"+
    "\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[7353];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** Error code for "Unknown internal scanner error". */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  /** Error code for "could not match input". */
  private static final int ZZ_NO_MATCH = 1;
  /** Error code for "pushback value was too large". */
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /**
   * Error messages for {@link #ZZ_UNKNOWN_ERROR}, {@link #ZZ_NO_MATCH}, and
   * {@link #ZZ_PUSHBACK_2BIG} respectively.
   */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\2\11\2\1\2\11\1\1\2\11\2\1\1\11"+
    "\1\1\1\11\3\1\1\11\4\1\3\11\15\1\1\11"+
    "\1\1\1\11\1\1\20\11\1\0\1\1\1\11\1\1"+
    "\2\0\3\11\25\1\1\11\2\0\1\11\3\1\1\0"+
    "\1\11\24\1\1\0\63\1\1\11";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[173];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** Input device. */
  private java.io.Reader zzReader;

  /** Current state of the DFA. */
  private int zzState;

  /** Current lexical state. */
  private int zzLexicalState = YYINITIAL;

  /**
   * This buffer contains the current text to be matched and is the source of the {@link #yytext()}
   * string.
   */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** Text position at the last accepting state. */
  private int zzMarkedPos;

  /** Current text position in the buffer. */
  private int zzCurrentPos;

  /** Marks the beginning of the {@link #yytext()} string in the buffer. */
  private int zzStartRead;

  /** Marks the last character in the buffer, that has been read from input. */
  private int zzEndRead;

  /**
   * Whether the scanner is at the end of file.
   * @see #yyatEOF
   */
  private boolean zzAtEOF;

  /**
   * The number of occupied positions in {@link #zzBuffer} beyond {@link #zzEndRead}.
   *
   * <p>When a lead/high surrogate has been read from the input stream into the final
   * {@link #zzBuffer} position, this will have a value of 1; otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /** Number of newlines encountered up to the start of the matched text. */
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  private int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  @SuppressWarnings("unused")
  private boolean zzEOFDone;

  /* user code: */
  public Token token;
  public StringBuilder string;
public String nextToken(){
        token = null;
      try{
          token = next();
          if(token.equals(null)){
              return "$";
          }
          switch(token.type){
              case (0):
                  if(token.token.equals("bool")||
                  token.token.equals("string")||
                  token.token.equals("int")||
                  token.token.equals("real")
                  ){
                      return token.token+"Literal";
                  }
                  return token.token;

              case (1):
                  return "id";

              case (2):
                  if(token.token.contains("x") || token.token.contains("X")){
                      return "Hexadecimal";
                  }else{
                     return "Decimal";
                  }

              case (3):
                   if(token.token.contains("e") || token.token.contains("E")){
                       return "Scientific";
                   }else{
                       return "Realnumber";
                   }

              case (7):
                  if(token.token.equals("\"")){
                      string = new StringBuilder();
                      do{
                          token = next();
                          string.append(token.token);
                      }while (!token.token.equals("\""));

                  return "String";
                  }if(token.token.equals(",")){
                      return "comma";
                  }
                  return token.token;

              default:
                  return null;

          }
      }catch(Exception e){
           return "$";
      }
}
  public class  Token {
          String token;
          int type;
          int line;
          int column;

          //reserved 0
          //Identifiers 1
          //Integer Numbers 2
          //Real Numbers 3
          //Strings 4
          //Special Characters  5
          //Comments  6
          //Operators and Punctuations  7
          //Undefined Token  8

            Token(String token , int type ){
                this.type = type;
                this.token = token;
                this.line = yyline;
                this.column = yycolumn;
            }
  }




  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Scanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  /**
   * Refills the input buffer.
   *
   * @return {@code false} iff there was new input.
   * @exception java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead - zzStartRead);

      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException(
          "Reader returned 0 characters. See JFlex examples/zero-reader for a workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
        if (numRead == requested) { // We requested too few chars to encode a full Unicode character
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        } else {                    // There is room in the buffer for at least one more char
          int c = zzReader.read();  // Expecting to read a paired low surrogate char
          if (c == -1) {
            return true;
          } else {
            zzBuffer[zzEndRead++] = (char)c;
          }
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }


  /**
   * Closes the input reader.
   *
   * @throws java.io.IOException if the reader could not be closed.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; // indicate end of file
    zzEndRead = zzStartRead; // invalidate buffer

    if (zzReader != null) {
      zzReader.close();
    }
  }


  /**
   * Resets the scanner to read from a new input stream.
   *
   * <p>Does not close the old reader.
   *
   * <p>All internal variables are reset, the old input stream <b>cannot</b> be reused (internal
   * buffer is discarded and lost). Lexical state is set to {@code ZZ_INITIAL}.
   *
   * <p>Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader The new input stream.
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzEOFDone = false;
    yyResetPosition();
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE) {
      zzBuffer = new char[ZZ_BUFFERSIZE];
    }
  }

  /**
   * Resets the input position.
   */
  private final void yyResetPosition() {
      zzAtBOL  = true;
      zzAtEOF  = false;
      zzCurrentPos = 0;
      zzMarkedPos = 0;
      zzStartRead = 0;
      zzEndRead = 0;
      zzFinalHighSurrogate = 0;
      yyline = 0;
      yycolumn = 0;
      yychar = 0L;
  }


  /**
   * Returns whether the scanner has reached the end of the reader it reads from.
   *
   * @return whether the scanner has reached EOF.
   */
  public final boolean yyatEOF() {
    return zzAtEOF;
  }


  /**
   * Returns the current lexical state.
   *
   * @return the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state.
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   *
   * @return the matched text.
   */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos-zzStartRead);
  }


  /**
   * Returns the character at the given position from the matched text.
   *
   * <p>It is equivalent to {@code yytext().charAt(pos)}, but faster.
   *
   * @param position the position of the character to fetch. A value from 0 to {@code yylength()-1}.
   *
   * @return the character at {@code position}.
   */
  public final char yycharat(int position) {
    return zzBuffer[zzStartRead + position];
  }


  /**
   * How many characters were matched.
   *
   * @return the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * <p>In a well-formed scanner (no or only correct usage of {@code yypushback(int)} and a
   * match-all fallback rule) this method will only be called with things that
   * "Can't Possibly Happen".
   *
   * <p>If this method is called, something is seriously wrong (e.g. a JFlex bug producing a faulty
   * scanner etc.).
   *
   * <p>Usual syntax/scanner level error handling should be done in error fallback rules.
   *
   * @param errorCode the code of the error message to display.
   */
  private static void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * <p>They will be read again by then next call of the scanning method.
   *
   * @param number the number of characters to be read again. This number must not be greater than
   *     {@link #yylength()}.
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }




  /**
   * Resumes scanning until the next regular expression is matched, the end of input is encountered
   * or an I/O-Error occurs.
   *
   * @return the next token.
   * @exception java.io.IOException if any I/O-Error occurs.
   */
  public Token next() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is
        // (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { return  new Token(yytext(),8);
            }
            // fall through
          case 54: break;
          case 2:
            { /* ignore */
            }
            // fall through
          case 55: break;
          case 3:
            { return new  Token("!" ,7);
            }
            // fall through
          case 56: break;
          case 4:
            { yybegin(STRING);
                                      return new  Token("\""  ,7);
            }
            // fall through
          case 57: break;
          case 5:
            { return  new Token("%" ,7);
            }
            // fall through
          case 58: break;
          case 6:
            { return new  Token("&" ,7);
            }
            // fall through
          case 59: break;
          case 7:
            { return new  Token("(" ,7);
            }
            // fall through
          case 60: break;
          case 8:
            { return  new Token(")" ,7);
            }
            // fall through
          case 61: break;
          case 9:
            { return new  Token("*" ,7);
            }
            // fall through
          case 62: break;
          case 10:
            { return new  Token("+" ,7);
            }
            // fall through
          case 63: break;
          case 11:
            { return new  Token("," ,7);
            }
            // fall through
          case 64: break;
          case 12:
            { return new  Token("-" ,7);
            }
            // fall through
          case 65: break;
          case 13:
            { return  new Token("." ,7);
            }
            // fall through
          case 66: break;
          case 14:
            { return  new Token("/" ,7);
            }
            // fall through
          case 67: break;
          case 15:
            { return new Token(yytext(),2);
            }
            // fall through
          case 68: break;
          case 16:
            { return  new Token(";" ,7);
            }
            // fall through
          case 69: break;
          case 17:
            { return new  Token("<" ,7);
            }
            // fall through
          case 70: break;
          case 18:
            { return new  Token("=" ,7);
            }
            // fall through
          case 71: break;
          case 19:
            { return new  Token(">" ,7);
            }
            // fall through
          case 72: break;
          case 20:
            { return new Token(yytext(),1);
            }
            // fall through
          case 73: break;
          case 21:
            { return new  Token("[" ,7);
            }
            // fall through
          case 74: break;
          case 22:
            { return  new Token("]" ,7);
            }
            // fall through
          case 75: break;
          case 23:
            { return  new Token("^" ,7);
            }
            // fall through
          case 76: break;
          case 24:
            { return new  Token("{" ,7);
            }
            // fall through
          case 77: break;
          case 25:
            { return  new Token("|" ,7);
            }
            // fall through
          case 78: break;
          case 26:
            { return  new Token("}" ,7);
            }
            // fall through
          case 79: break;
          case 27:
            { return new  Token( yytext() ,4);
            }
            // fall through
          case 80: break;
          case 28:
            { yybegin(YYINITIAL);
                                       return new  Token(yytext(),7);
            }
            // fall through
          case 81: break;
          case 29:
            { yybegin(STRINGBACK);
            }
            // fall through
          case 82: break;
          case 30:
            { yybegin(STRING);return new Token("\\"+yytext(),4);
            }
            // fall through
          case 83: break;
          case 31:
            { yybegin(STRING);return  new Token("\\\"",5);
            }
            // fall through
          case 84: break;
          case 32:
            { yybegin(STRING);return  new Token("\\\'",5);
            }
            // fall through
          case 85: break;
          case 33:
            { yybegin(STRING);return   new Token("\\\\",5);
            }
            // fall through
          case 86: break;
          case 34:
            { yybegin(STRING);return  new Token("\\n",5);
            }
            // fall through
          case 87: break;
          case 35:
            { yybegin(STRING);return  new Token("\\r",5);
            }
            // fall through
          case 88: break;
          case 36:
            { yybegin(STRING);return  new Token("\\t",5);
            }
            // fall through
          case 89: break;
          case 37:
            { return new  Token("!=",7);
            }
            // fall through
          case 90: break;
          case 38:
            { return new  Token("&&",7);
            }
            // fall through
          case 91: break;
          case 39:
            { return new  Token("*=",7);
            }
            // fall through
          case 92: break;
          case 40:
            { return new  Token("++",7);
            }
            // fall through
          case 93: break;
          case 41:
            { return new  Token("+=",7);
            }
            // fall through
          case 94: break;
          case 42:
            { return  new Token("--",7);
            }
            // fall through
          case 95: break;
          case 43:
            { return new  Token("-=",7);
            }
            // fall through
          case 96: break;
          case 44:
            { return  new Token(yytext(),6);
            }
            // fall through
          case 97: break;
          case 45:
            { return  new Token("/=",7);
            }
            // fall through
          case 98: break;
          case 46:
            { return  new Token(yytext(),3);
            }
            // fall through
          case 99: break;
          case 47:
            { return  new Token("<=",7);
            }
            // fall through
          case 100: break;
          case 48:
            { return  new Token("==",7);
            }
            // fall through
          case 101: break;
          case 49:
            { return  new Token(">=",7);
            }
            // fall through
          case 102: break;
          case 50:
            { return new Token(yytext(),0);
            }
            // fall through
          case 103: break;
          case 51:
            { return  new Token("||",7);
            }
            // fall through
          case 104: break;
          case 52:
            { return new  Token(yytext(),3);
            }
            // fall through
          case 105: break;
          case 53:
            { return  new Token(yytext(),2);
            }
            // fall through
          case 106: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}