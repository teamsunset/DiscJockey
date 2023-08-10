package com.sunset.discjockey.block.BlockEntity;

import com.sunset.discjockey.client.audio.SpeakerSound;
import com.sunset.discjockey.util.RegistryCollection.BlockEntityTypeCollection;
import com.sunset.discjockey.util.RegistryCollection.ItemCollection;
import com.sunset.discjockey.util.MusicMisc.MusicFileManager;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.concurrent.CompletableFuture;

import static com.sunset.discjockey.DiscJockey.SG_LOGGER;
import static net.minecraft.world.level.block.Block.UPDATE_CLIENTS;

public class BlockEntityDDJ400 extends BlockEntity
{

    private String songURL = "https://music.163.com/song/media/outer/url?id=32405293.mp3";

    private boolean playing = false;

    private SpeakerSound speakerSound = null;

    public BlockEntityDDJ400(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeCollection.BLOCK_ENTITY_DDJ400.get(), pPos, pBlockState);
    }

    public boolean getPlaying() {
        return playing;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setPlaying(boolean playing) {
        if (this.playing != playing) {
            this.playing = playing;
            this.markDirty();
        }
    }


    public void setSongURL(String songURL) {
        if (!this.songURL.equals(songURL)) {
            this.songURL = songURL;
            this.markDirty();
        }
    }

    //action
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).getItem().equals(ItemCollection.ITEM_USB_FLASH_DISK.get())) {
            String futureSongURL = player.getItemInHand(hand).getDisplayName().getString().replace("[", "").replace("]", "");
            if (!level.isClientSide()) {
                this.setSongURL(futureSongURL);
                player.sendSystemMessage(Component.literal("Set song url to " + this.getSongURL()));
            } else {
                CompletableFuture.runAsync(() -> {
                            if (MusicFileManager.getFileFromUrl(futureSongURL) == null) {
                                player.sendSystemMessage(Component.literal("§4Song not found!"));
                            }
                        }
                        , Util.backgroundExecutor());
            }
        } else if (!level.isClientSide()) {
            SG_LOGGER.debug(String.valueOf(hit.getLocation()));
            this.setPlaying(!this.getPlaying());
            player.displayClientMessage(Component.literal("Set playing to " + this.getPlaying()), true);
        }
        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void play() {
        CompletableFuture.runAsync(() ->
//                        Minecraft.getInstance().submitAsync(() ->
                {
                    try {
                        this.speakerSound = new SpeakerSound(this.getBlockPos(), this.getSongURL());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    Minecraft.getInstance().getSoundManager().play(this.speakerSound);
                }
//                        )
                , Util.backgroundExecutor());
    }


    //data change actions
    public void markDirty() {
        if (level != null) {
            this.setChanged();
            this.sync();
        }
    }

    public void sync() {
        if (level != null && !level.isClientSide)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), UPDATE_CLIENTS);
    }

    //all getPacket function use getUpdateTag() to get data
    //save actions
    @Override
    public void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        CompoundTag buildTag = getUpdateTag();
        for (String key : buildTag.getAllKeys()) {
            compoundTag.put(key, buildTag.get(key));
        }
    }


    @Override
    public void load(CompoundTag compoundTag) throws RuntimeException {
        super.load(compoundTag);
        boolean previousPlaying = this.getPlaying();
        this.setPlaying(compoundTag.getBoolean("playing"));
        this.setSongURL(compoundTag.getString("songURL"));
        if (level != null && level.isClientSide() && (previousPlaying != this.getPlaying()) && this.getPlaying()) {
            this.play();
        }
    }

    //network
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundTag = super.getUpdateTag();
        compoundTag.putBoolean("playing", this.getPlaying());
        compoundTag.putString("songURL", this.getSongURL());
        return compoundTag;
    }

    //super use load()
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
//        super.handleUpdateTag(compoundTag);

//        super.load(compoundTag);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // Will get tag from #getUpdateTag
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
