package net.minecraft.src;

import java.util.Comparator;
import java.util.List;

public class Score {
   public static final Comparator field_96658_a = new ScoreComparator();
   private final Scoreboard theScoreboard;
   private final ScoreObjective theScoreObjective;
   private final String field_96654_d;
   private int field_96655_e;

   public Score(Scoreboard par1Scoreboard, ScoreObjective par2ScoreObjective, String par3Str) {
      this.theScoreboard = par1Scoreboard;
      this.theScoreObjective = par2ScoreObjective;
      this.field_96654_d = par3Str;
   }

   public void func_96649_a(int par1) {
      if(this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.func_96647_c(this.getScorePoints() + par1);
      }
   }

   public void func_96646_b(int par1) {
      if(this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.func_96647_c(this.getScorePoints() - par1);
      }
   }

   public void func_96648_a() {
      if(this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.func_96649_a(1);
      }
   }

   public int getScorePoints() {
      return this.field_96655_e;
   }

   public void func_96647_c(int par1) {
      int var2 = this.field_96655_e;
      this.field_96655_e = par1;
      if(var2 != par1) {
         this.func_96650_f().func_96536_a(this);
      }
   }

   public ScoreObjective func_96645_d() {
      return this.theScoreObjective;
   }

   public String getPlayerName() {
      return this.field_96654_d;
   }

   public Scoreboard func_96650_f() {
      return this.theScoreboard;
   }

   public void func_96651_a(List par1List) {
      this.func_96647_c(this.theScoreObjective.getCriteria().func_96635_a(par1List));
   }
}
